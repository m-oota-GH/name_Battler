package com.example.namebattler.function.battle

import com.example.namebattler.function.enemy.EnemyParameterProduction
import com.example.namebattler.function.job.JobParameterProduction
import com.example.namebattler.function.Belong
import com.example.namebattler.function.EndEnum
import com.example.namebattler.function.OperationIdEnum
import com.example.namebattler.function.TargetIdEnum
import com.example.namebattler.function.viewModel.BattleViewModel


class OverallProgressOfBattle(private var battleViewModel: BattleViewModel) {


    private var initiative: Map<Pair<String, String>, Int> = mutableMapOf()

    private var enemyList = mutableListOf<CharacterInformationHolder>()
    private var playerList = mutableListOf<CharacterInformationHolder>()

    private var deadList = arrayListOf<CharacterInformationHolder>()

    fun initCharacterList(
        enemyObj: ArrayList<CharacterInformationHolder>,
        partyObj: ArrayList<CharacterInformationHolder>
    ) {
        //初期化処理
        this.enemyList = mutableListOf()
        this.enemyList = enemyObj

        //初期化処理
        this.playerList = mutableListOf()
        this.playerList = partyObj
    }

    /** 行動順決め **/
    fun initInitiative() {

        //player、enemyをまとめる
        val characterList = mutableListOf<CharacterInformationHolder?>()
        characterList.addAll(enemyList)
        characterList.addAll(playerList)


        //AGIの高い順に所属、名前、AGIをinitiativeに格納していく
        initiative = characterList.associate { Pair(it!!.belong, it.name) to (it.agi) }.toList()
            .sortedBy { it.second }.reversed().toMap()
    }

    /** 戦闘処理 **/
    fun turnProgressOfBattle(sendToOperation: String) {
        val resultLog = mutableListOf<String>()
        var operation : String

        //ターン経過処理
        var count = battleViewModel.currentTurn.value ?:0
        count++

        //battleViewModelから前のターンのログ情報を取得
        resultLog.addAll(battleViewModel.battleLogData.value?: mutableListOf())

        resultLog.add("--------[$count ターン]--------")

        val instanceOfBattleProcess = BattleProcessDetails()
        instanceOfBattleProcess.setBattleProcessInformation(battleViewModel.informationNotice.value?: arrayListOf(), deadList)
        run loop@{
            //行動順に行動する
            initiative.forEach { map ->
                //途中で勝敗が決した場合処理を抜け出す
                val isInterruption = isEnding()
                if(isInterruption != ""){
                    return@loop
                }
                val pair = map.key
                val doerCharacter = selectActionCharacter(pair)!!

                //エネミーの作戦を決定
                operation = if (doerCharacter.belong == Belong.ENEMY.name){
                    EnemyParameterProduction().selectEnemyOperation()
                }else {
                    sendToOperation
                }

                //行為者のIDを取得
                val doerId = doerCharacter.id

                //行為者が戦闘可能かどうか確認
                var isLiving = true
                val hp = doerCharacter.currentHp
                if (hp < 1){
                    isLiving = false
                }else{
                    resultLog.add("")
                }
                if (isLiving){
                    resultLog.add("${doerCharacter.name}の行動")

                    val resultAndLog = instanceOfBattleProcess.conditionProcess(doerId, doerCharacter)
                    resultLog.addAll(resultAndLog.second)

                    if (resultAndLog.first) {
                        val selectAttackResult = selectAttackResult(operation, doerCharacter)

                        val isEmptyEnemyList = selectAttackResult.second

                        //敵が全員先頭不能時に途中で処理を抜けれるように
                        if(isEmptyEnemyList.first == "NONE"){
                            return@loop
                        }
                        //行動結果を取得
                        val attackResult = selectAttackResult.first
                        //攻撃倍率に経過ターン数を格納
                        attackResult.attackMagnification = battleViewModel.currentTurn.value?:0 - 1

                        val selectSkill = selectAttackResult.second
                        //ログ情報設定[行動内容]
                        resultLog.addAll(attackResult.flavorText)
                        //標的情報(複数含む)
                        val targetList = selectSkill.second
                        //標的用の識別ID

                        //行動結果を反映
                        when (attackResult.targetId) {
                            TargetIdEnum.ONE_ATTACK.id, TargetIdEnum.ALL_ATTACK.id -> {
                                val log = instanceOfBattleProcess.attackProcess(targetList, attackResult)
                                resultLog.addAll(log)
                            }
                            TargetIdEnum.MYSELF.id -> {
                                //防御アップのみ
                                val log = instanceOfBattleProcess.myselfSupportProcess(doerId, attackResult)
                                resultLog.addAll(log)
                            }
                            TargetIdEnum.ONE_SUPPORT.id -> {
                                val log = instanceOfBattleProcess.supportProcess(targetList, attackResult)
                                resultLog.addAll(log)
                            }
                        }
                        //MPコスト支払い(マイナスになる場合は0を格納)
                        instanceOfBattleProcess.mpPayment(doerId, attackResult)
                    }

                    //行動中のキャラのコンディション情報更新
                    instanceOfBattleProcess.updateCurrentCondition(doerId)
                }
            }
        }

        //現在のターンを格納
        battleViewModel.currentTurn.postValue(count)

        //ログ情報を格納
        battleViewModel.battleLogData.postValue(resultLog)
        //キャラクター情報をLiveDataへ格納
        battleViewModel.setInformationNotice(instanceOfBattleProcess.getBattleProcessInformation())

    }

    /** 行動処理決め **/
    private fun selectAttackResult(
        operation: String,
        actionCharacter: CharacterInformationHolder
    ): Pair<ActionResultHolder, Pair<String, MutableList<CharacterInformationHolder>>> {

        //selectSkillで使用スキルと対象者を選定する
        /** selectSkill param
         * ( first : skillName , second =  target) **/
        val selectSkill: Pair<String, MutableList<CharacterInformationHolder>>

        //行動結果
        var attackResult = ActionResultHolder()

        val actorJob = actionCharacter.job.let { JobParameterProduction().getJobInstance(it) }

        //このタイミングで戦闘不能者をターゲットからはじく
        val targetList = battleViewModel.informationNotice.value?.filterNot { it.currentHp <= 0 } as ArrayList<CharacterInformationHolder>


        selectSkill = actorJob!!.selectSkill(operation, actionCharacter, targetList)

        if (selectSkill.first != "NONE"){
            when (operation) {
                OperationIdEnum.OFFENSIVE.text -> attackResult =
                    actorJob.offensiveAction(actionCharacter, selectSkill.first)
                OperationIdEnum.DEFENSIVE.text -> attackResult =
                    actorJob.defensiveAction(actionCharacter, selectSkill.first)
                OperationIdEnum.FLEXIBLE.text -> attackResult =
                    actorJob.flexibleAction(actionCharacter, selectSkill.first)
            }
        }

        return Pair(attackResult, selectSkill)
    }

    /** 勝敗確認 **/
    fun isEnding(): String{

        var ending = ""

        var deadEnemyNum = 0
        var deadPlayerNum = 0

        //戦闘不能者をカウント
        // 戦闘不能者が規定数に足していたら勝敗を返す
        deadList.forEach {
            if (it.belong == Belong.ENEMY.name){
                deadEnemyNum ++
            }else if (it.belong == Belong.PLAYER.name){
                deadPlayerNum ++
            }
        }
        if (deadEnemyNum == 3){
            ending = EndEnum.WIN.name
        }else if (deadPlayerNum == 3){
            ending = EndEnum.LOSE.name
        }

        return ending
    }

    /** initiativeの情報から行為者の情報を抽出する **/
    private fun selectActionCharacter(initiativeKey: Pair<String, String>): CharacterInformationHolder? {
        var setList = mutableListOf<CharacterInformationHolder?>()

        val belong = initiativeKey.first
        val name = initiativeKey.second

        when (belong) {
            Belong.PLAYER.name -> setList = playerList.toMutableList()
            Belong.ENEMY.name -> setList = enemyList.toMutableList()
        }
        return setList.find { it!!.name == name }
    }

}


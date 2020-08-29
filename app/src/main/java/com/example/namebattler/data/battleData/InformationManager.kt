package com.example.namebattler.data.battleData

import androidx.lifecycle.MutableLiveData
import com.example.namebattler.data.characterData.CharacterHolder
import com.example.namebattler.util.Belong
import com.example.namebattler.util.TotalIndexEnum

class InformationManager {

    //ユニークなオブジェクトを渡す
    fun getInstance(): InformationManager {
        return instance
    }

    var informationNotice = MutableLiveData<ArrayList<CharacterInformationHolder>>()

    private var characterInfomation = mutableListOf <CharacterInformationHolder>()

    fun setInformationNotice(characterInformationList: ArrayList<CharacterInformationHolder>) {
        informationNotice.postValue(characterInformationList)
    }

    private fun Int.setOutputInformationHolder(holder: CharacterHolder, condition :MutableMap<String,Int>): CharacterInformationHolder {
        return CharacterInformationHolder(
            this,
            holder.belong,
            holder.name,
            holder.job,
            holder.hp,
            holder.hp,
            holder.mp,
            holder.mp,
            holder.str,
            0,
            holder.def,
            0,
            holder.agi,
            0,
            holder.luck,
            0,
            condition
        )
    }

    private fun Int.setOutputInformationHolder(holder: CharacterInformationHolder?): CharacterInformationHolder {
        return CharacterInformationHolder(
            this,
            holder!!.belong,
            holder.name,
            holder.job,
            holder.maxHp,
            holder.currentHp,
            holder.maxMp,
            holder.currentMp,
            holder.str,
            holder.effectTimeOfStr,
            holder.def,
            holder.effectTimeOfDef,
            holder.agi,
            holder.effectTimeOfAgi,
            holder.luck,
            holder.effectTimeOfLuck,
            holder.cond
        )
    }

    fun getOutputInformationList(
        enemy: ArrayList<CharacterInformationHolder>,
        player: ArrayList<CharacterInformationHolder>
    ): ArrayList<CharacterInformationHolder> {
        val firstEnemyHolder = TotalIndexEnum.FIRST_ENEMY.id.setOutputInformationHolder(enemy[0])
        val secondEnemyHolder = TotalIndexEnum.SECOND_ENEMY.id.setOutputInformationHolder(enemy[1])
        val thirdEnemyHolder = TotalIndexEnum.THIRD_ENEMY.id.setOutputInformationHolder(enemy[2])

        val firstPlayerHolder = TotalIndexEnum.FIRST_PLAYER.id.setOutputInformationHolder(player[0])
        val secondPlayerHolder = TotalIndexEnum.SECOND_PLAYER.id.setOutputInformationHolder(player[1])
        val thirdPlayerHolder = TotalIndexEnum.THIRD_PLAYER.id.setOutputInformationHolder(player[2])

        return arrayListOf(
            firstEnemyHolder,
            secondEnemyHolder,
            thirdEnemyHolder,
            firstPlayerHolder,
            secondPlayerHolder,
            thirdPlayerHolder
        )
    }

    fun initOutputInformationList(characterHolder: ArrayList<CharacterHolder>)
            : ArrayList<CharacterInformationHolder> {

        val belong = characterHolder[0].belong

        var firstHolder : CharacterInformationHolder? = null
        var secondHolder : CharacterInformationHolder? = null
        var thirdHolder : CharacterInformationHolder? = null

        if (belong == Belong.ENEMY.name){
            firstHolder = TotalIndexEnum.FIRST_ENEMY.id.setOutputInformationHolder(characterHolder[0],
                mutableMapOf()
            )
            secondHolder = TotalIndexEnum.SECOND_ENEMY.id.setOutputInformationHolder(characterHolder[1],
                mutableMapOf())
            thirdHolder = TotalIndexEnum.THIRD_ENEMY.id.setOutputInformationHolder(characterHolder[2],
                mutableMapOf())
        } else if (belong == Belong.PLAYER.name){
            firstHolder = TotalIndexEnum.FIRST_PLAYER.id.setOutputInformationHolder(characterHolder[0],
                mutableMapOf())
            secondHolder = TotalIndexEnum.SECOND_PLAYER.id.setOutputInformationHolder(characterHolder[1],
                mutableMapOf())
            thirdHolder = TotalIndexEnum.THIRD_PLAYER.id.setOutputInformationHolder(characterHolder[2],
                mutableMapOf())

    }
        return arrayListOf(firstHolder!!, secondHolder!!, thirdHolder!!)

    }


    private fun resetCharacterHolder(holder: CharacterInformationHolder ):CharacterHolder{
        return CharacterHolder(
            holder.belong,
            holder.name,
            holder.job,
            holder.maxHp,
            holder.maxMp,
            holder.str,
            holder.def,
            holder.agi,
            holder.luck,
            0)
    }


    fun resetCharacterList(CharacterInformationHolder: ArrayList<CharacterInformationHolder> ):ArrayList<CharacterHolder>{

        val firstHolder : CharacterHolder?
        val secondHolder : CharacterHolder?
        val thirdHolder : CharacterHolder?

        firstHolder = resetCharacterHolder(CharacterInformationHolder[0])
        secondHolder = resetCharacterHolder(CharacterInformationHolder[1])
        thirdHolder = resetCharacterHolder(CharacterInformationHolder[2])

        return arrayListOf(firstHolder, secondHolder, thirdHolder)
    }

   //インスタンス生成
    companion object {
        val instance = InformationManager()
    }




}
package com.example.namebattler.function.action.meleeAttack

import com.example.namebattler.function.action.ActionHolder
import com.example.namebattler.function.battle.CharacterInformationHolder

class MaceAttack : AttackActionInterface {
    override fun getSkillData(character: CharacterInformationHolder): ActionHolder {
        return activeAction(character)
    }

    override fun activeAction(character: CharacterInformationHolder): ActionHolder {
        //攻撃基準値
        //buff,deBuffの適用
        val correctionStrValue = character.effectTimeOfStr
        var str = character.str
        if (correctionStrValue > 0) {
            str = str * 3 / 2
        } else if (correctionStrValue < 0) {
            str = str * 2 / 3
        }

        //命中基準値
        //buff,deBuffの適用
        val correctionLuckVale = character.effectTimeOfLuck
        var luck = character.luck
        if (correctionLuckVale > 0) {
            luck = luck * 3 / 2
        } else if (correctionLuckVale < 0) {
            luck = luck * 2 / 3
        }
        val hitRateStandard = (getPercent(luck) / 4) + (getPercent(luck) / 2)

        //命中率（低目有利）
        val hitRate = (0..100).random()
        //命中値（攻撃補正値）
        val hittingPoint: Int

        //クリティカル成否
        var isCritical = false

        //クリティカルヒット
        if (hitRate <= hitRateStandard / 10) {
            isCritical = true
            hittingPoint = str / 2
        } else {
            hittingPoint = (100 - hitRate) / 100 * hitRateStandard
        }

        val attackPoint = str + hittingPoint

        return ActionHolder(
            "メイスで攻撃した",
            attackPoint,
            0,
            isCritical,
            Pair("", 0)
        )
    }

    override fun getPercent(num: Int?): Int {
        return num!! * 100 / 255
    }

    override fun getPercent(num: Int?, standardValue: Int): Int {
        return num!! * 100 / standardValue
    }
}
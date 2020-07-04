package com.example.namebattler.data.jobData

private const val SUPPLY_HP = 10
private const val SUPPLY_MP = 25
private const val SUPPLY_STR = 0
private const val SUPPLY_DEF = 15
private const val SUPPLY_AGI = 5
private const val SUPPLY_LUCK = 20

class SpellCaster : JobManager.JobAbstract() {

    override var hp : Int = 0
    override var mp : Int = 0
    override var str : Int = 0
    override var def : Int = 0
    override var agi : Int = 0
    override var luck : Int = 0

    override var supplyBox: List<Int> =
        listOf(SUPPLY_HP, SUPPLY_MP, SUPPLY_STR, SUPPLY_DEF, SUPPLY_AGI, SUPPLY_LUCK)

    override fun addParam() {
        this.uniqueVale()
    }

    override fun uniqueVale() {
        this.hp = supplyBox[0]
        this.mp = supplyBox[1]
        this.str = supplyBox[2]
        this.def = supplyBox[3]
        this.agi = supplyBox[4]
        this.luck = supplyBox[5]
    }
}
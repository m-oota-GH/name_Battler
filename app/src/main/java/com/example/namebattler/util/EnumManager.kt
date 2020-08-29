package com.example.namebattler.util

enum class JobEnum (val jobName :String){
    WARRIOR("戦士"),
    SPELL_CASTER("魔法使い"),
    PRIEST("僧侶"),
    BERSERK("バーサーカー"),
}

enum class EndEnum{
    WIN,
    LOSE
}

enum class TotalIndexEnum(val id: Int) {
    FIRST_ENEMY(0),
    SECOND_ENEMY(1),
    THIRD_ENEMY(2),
    FIRST_PLAYER(3),
    SECOND_PLAYER(4),
    THIRD_PLAYER(5),
}

enum class Belong{
    PLAYER,
    ENEMY
}

enum class OperationIdEnum(val id: Int, val text: String) {
    OFFENSIVE (0, "ガンガンいこうぜ"),
    DEFENSIVE(1, "いのちだいじに"),
    FLEXIBLE(2, "バッチリがんばれ"),
}

enum class ConditionEnum(val id: Int, val text: String) {
    FRESH(0,"治癒"),
    SLEEP(1,"眠り"),
    PARALYSIS(2,"麻痺"),
    SCALD(3,"炎上"),
}

enum class SKillEnum(val id: Int) {
    ONE_MELEE_ATTACK(0),
    SLEEP_CLOUD(1),
    THUNDERBOLT(2),
    FIREBALL(3),
    GUARD(4),
    REFRESH(5),
    CURE_WOUNDS(6),
}

enum class DamageTypeEnum{
    NO_DAMAGE,
    NORMAL_DAMAGE,
    NON_LETHAL_DAMAGE
}

enum class TargetIdEnum(val id: Int){
    ONE_ATTACK(0),
    ALL_ATTACK(1),
    MYSELF(2),
    ONE_SUPPORT(3),
    ALL_SUPPORT(4),
}
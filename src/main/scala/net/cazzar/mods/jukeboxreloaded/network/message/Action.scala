package net.cazzar.mods.jukeboxreloaded.network.message

object Action extends Enumeration {
  val PLAY, STOP, NEXT, PREVIOUS, SHUFFLE_ON, SHUFFLE_OFF, REPEAT_ALL, REPEAT_NONE, REPEAT_ONE = Value
}
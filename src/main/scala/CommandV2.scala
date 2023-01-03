import scala.collection.mutable.ListBuffer
import scala.util.chaining._

object CommandV2 extends App {

  // リモコンは7つのonとoffコマンドを扱う。コマンドを対応するリストに格納する。

  // コマンドの抽象定義
  trait Command {
    // アクションをカプセル化するメソッド
    def execute: Unit
  }
  class NoCommand extends Command {
    def execute = ???
  }

  // コマンドの具象定義
  class LightOnCommand(light: Light) extends Command {
    def execute = light.on
  }
  class LightOffCommand(light: Light) extends Command {
    def execute = light.off
  }

  class StereoOnCommand(stereo: Stereo) extends Command {
    def execute = stereo.on
  }
  class StereoOffCommand(stereo: Stereo) extends Command {
    def execute = stereo.off
  }

  // デバイスの定義。ベンダークラスの実装。
  class Light(location: String) {
    def on = println(s"${location} light is on")
    def off = println(s"${location} light is off")
  }

  class Stereo(location: String) {
    def on = println(s"${location} stereo is on")
    def off = println(s"${location} stereo is on")
    def setVolume(volume: Int) = println(s"${location} stereo volume set to ${volume}")
  }


  // リモコンを実装
  class RemoteControl {
    // 初期段階では、全てNoCommandが設定されている。「nullオブジェクト」使用の一例
    val initialCommands = {
      val onCommands: List[Command] = List.fill(7)(new NoCommand)
      val offCommands: List[Command] = List.fill(7)(new NoCommand)
      (onCommands, offCommands)
    }

    /**
     * コマンドを設定する
     * @param slot スロット位置
     * @param onCommand 格納するonコマンド
     * @param offCommand 格納するoffコマンド
     */
    def setCommand(onCommands: List[Command], offCommands: List[Command])(slot: Int, onCommand: Command, offCommand: Command): (List[Command], List[Command]) = {
      (onCommands.updated(slot, onCommand), offCommands.updated(slot, offCommand))
    }

    // 指定位置のコマンドを実装
    def onButtonWasPushed(onCommands: List[Command], slot: Int) = onCommands(slot).execute
    def offButtonWasPushed(offCommands: List[Command], slot: Int) = offCommands(slot).execute

    def toString(onCommands: List[Command], offCommands: List[Command]): String = {
      val stringBuff = new ListBuffer[String]
      stringBuff.append("\n---- リモコン ----\n")
      onCommands.zip(offCommands).zipWithIndex.foreach { case ((onCommand, offCommand), i) =>
        stringBuff.append(s"[スロット ${i}] ${onCommand.getClass.getName} ${offCommand.getClass.getName} \n")
      }
      stringBuff.toString()
    }
  }


  // 使用してみる
  // デバイスを作成
  val livingRoomLight = new Light("リビングルーム")
  val kitchenLight = new Light("キッチン")
  val stereo = new Stereo("リビングルーム")

  // コマンドオブジェクトを作成
  val livingRoomLightOn = new LightOnCommand(livingRoomLight)
  val livingRoomLightOff = new LightOffCommand(livingRoomLight)
  val kitchenLightOn = new LightOnCommand(kitchenLight)
  val kitchenLightOff = new LightOffCommand(kitchenLight)
  val stereoOn = new StereoOnCommand(stereo)
  val stereoOff = new StereoOffCommand(stereo)

  // リモコンを作成
  val remoteControl = new RemoteControl
  // リモコンの各ボタンにコマンドを割当
  val (onCommands, offCommands) = {
    remoteControl.initialCommands
      .pipe { case (ons, offs) => remoteControl.setCommand(ons, offs)(0, livingRoomLightOn, livingRoomLightOff)}
      .pipe { case (ons, offs) => remoteControl.setCommand(ons, offs)(1, kitchenLightOn, kitchenLightOff)}
      .pipe { case (ons, offs) => remoteControl.setCommand(ons, offs)(2, stereoOn, stereoOff)}
  }

  println(remoteControl.toString(onCommands, offCommands))

  remoteControl.onButtonWasPushed(onCommands, 0)
  remoteControl.offButtonWasPushed(onCommands, 0)
  remoteControl.onButtonWasPushed(onCommands, 1)
  remoteControl.offButtonWasPushed(onCommands, 1)
  remoteControl.onButtonWasPushed(onCommands, 2)
  remoteControl.offButtonWasPushed(onCommands, 2)

}

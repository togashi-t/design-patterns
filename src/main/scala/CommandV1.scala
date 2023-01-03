object CommandV1 extends App {

  // コマンド（注文）
  trait Command {
    // アクションをカプセル化するメソッド（orderUp）
    def execute: Unit
  }
  // コンストラクタにはコマンドが制御する特定の照明を渡している。executeが呼び出されたときにレシーバとなるのは、この照明オブジェクト。
  class LightOnCommand(light: Light) extends Command {
    // アクションとレシーバは、コマンドオブジェクト内で結合される。
    def execute = light.on // レシーバのアクションを起動
  }
  class GarageDoorOpenCommand(garageDoor: GarageDoor) extends Command {
    def execute = garageDoor.up
  }

  // レシーバ（コックさん）
  class Light {
    def on = println("照明が点いています")
    def off = ???
  }
  class GarageDoor {
    def up = println("ガレージのドアは開いています")
    def down = ???
    def stop = ???
    def light = ???
    def lightOff = ???
  }

  // インボーカ（ウェイターさん）
  class SimpleRemoteControl {
    // テキストで行っている、slotの型のみ定義、slotの値を書換可能、を実現するためにvarを使っている
    var slot: Option[Command] = None
    // スロットによって制御されるコマンドを設定するメソッド。このメソッドは、クライアントがリモコンボタンの振る舞いを変更したい場合に何度でも呼び出せる。（takeOrder）
    def setCommand(command: Command) = slot = Some(command)
    // ボタンを押すと呼び出されるメソッド
    def buttonWasPressed: Unit = slot.map(_.execute).get // 横着してgetを使用
  }



  // 実際に使ってみる。使う者がクライアント（客）。
  {
    // コマンドオブジェクトを作成
    //　電気
    val light = new Light // レシーバを作成
    val lightOn = new LightOnCommand(light) // レシーバをコマンドに渡す
    // ガレージ
    val garageDoor = new GarageDoor
    val garageOpen = new GarageDoorOpenCommand(garageDoor)

    // インボーカ(リモコン)を作成
    val remote = new SimpleRemoteControl

    // インボーカにコマンドオブジェクトを格納して実行
    // 電気
    remote.setCommand(lightOn)
    remote.buttonWasPressed
    // ガレージ
    remote.setCommand(garageOpen)
    remote.buttonWasPressed
  }


}

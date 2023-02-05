object Facade extends App {

  // Facadeの目的はサブシステムに対する単純化されたインタフェースをクライアントに用意すること
  class HomeTheaterFacade(
    amp: Amplifier,
    tuner: Tuner,
    player: StreamingPlayer,
    projector: Projector,
    lights: TheaterLights,
    screen: Screen,
    popper: PopcornPopper
  ) {

    def watchMovie(movie: String) = {
      println("映画を観る準備をします")
      popper.on
      popper.pop
      lights.dim
      screen.down
      projector.on
      projector.wideScreenMode
      amp.on
      amp.setStreamingPlayer(player)
      amp.setSurroundSound
      amp.setVolume
      player.on
      player.play(movie)
    }

    def endMovie = {
      println("ムービーシアターを停止します")
      popper.off
      lights.on
      screen.up
      projector.off
      amp.off
      player.stop
      player.off
    }

  }

  class Amplifier {
    def on = ???
    def setStreamingPlayer(player: StreamingPlayer) = ???
    def setSurroundSound = ???
    def setVolume = ???
    def off = ???
  }
  class Tuner
  class StreamingPlayer {
    def on = ???
    def play(movie: String) = ???
    def stop = ???
    def off = ???
  }
  class Projector {
    def on = ???
    def wideScreenMode = ???
    def off = ???
  }
  class TheaterLights {
    def on = ???
    def dim = ???
  }
  class Screen {
    def down = ???
    def up = ???
  }
  class PopcornPopper {
    def on = ???
    def pop = ???
    def off = ???
  }




  // 最小知識の原則。クラス間の依存関係を少なくする。
  // 従わない場合
  def getTemp(station: Station) = {
    val thermometer = station.getThermometer
    thermometer.getTemperature
  }
  // 従う場合
  def getTempOk(station: Station) = {
    station.getTemperature // 温度計に温度を要求するメソッドをStationクラスに追加するので、依存するクラス数が減少する。
  }

  class Station(thermometer: Thermometer) {
    def getThermometer = thermometer
    def getTemperature = thermometer.getTemperature
  }
  class Thermometer {
    def getTemperature = ???
  }


}

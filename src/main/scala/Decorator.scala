
object Decorator extends App {

  // 飲み物の抽象クラス
  abstract class Beverage {
    val description: String
    def getDescription: String = description
    val cost: Double
  }
  // 飲み物の具象クラス
  class Espresso extends Beverage {
    val description = "エスプレッソ"
    val cost = 1.99
  }
  class HouseBlend extends Beverage {
    val description = "ハウスブレンドコーヒー"
    val cost = 0.89
  }

  // コンディメント（デコレータ）の抽象クラス
  // Beverageと交換可能とするため、Beverageクラスを拡張している
  abstract class CondimentDecorator extends Beverage {
    // 各デコレータがラップするBeverage。
    val beverage: Beverage
    def getDescription: String
  }
  // コンディメントの具象クラス
  // コンストラクタのbeverageにvalを付けているのは、同名のフィールドがあるため
  class Mocha(val beverage : Beverage) extends CondimentDecorator {
    val description = beverage.description
    override def getDescription: String = beverage.getDescription + "、モカ"
    val cost = beverage.cost + 0.20
  }
  class Whip(val beverage : Beverage) extends CondimentDecorator {
    val description = beverage.description
    override def getDescription: String = beverage.getDescription + "、ホイップ"
    val cost = beverage.cost + 0.15
  }

  val b = new Espresso
  val b_ = new Mocha(b)
  val b__ = new Whip(b_)
  println(b__.getDescription)
  println(b__.cost)
}

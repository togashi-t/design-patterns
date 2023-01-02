object Factory extends App {

  // 作成するオブジェクトをサブクラスに決定させることによって、オブジェクト作成をカプセル化する。

  // 作成者クラス
  // 抽象作成者クラス
  abstract class PizzaStore {
    def orderPizza(typeName: String) = {
      val pizza = createPizza(typeName)
      // どの具象製品が作成されたかは知らない。しかし、Pizzaであることは知っており、Pizzaに共通する処理を行う。
      pizza.prepare
      pizza.bake
      pizza.cut
      pizza.box
      pizza
    }

    // 抽象ファクトリメソッド
    def createPizza(typeName: String): Pizza
  }
  // 具象作成者クラス
  class NYPizzaStore extends PizzaStore {
    def createPizza(typeName: String) = {
      if (typeName == "チーズ") NYStyleCheesePizza
      else if (typeName == "野菜") NYStyleVegetablePizza
      else NYStyleClamPizza
    }
  }
  class ChicagoPizzaStore extends PizzaStore {
    def createPizza(typeName: String) = {
      if (typeName == "チーズ") ChicagoStyleCheesePizza
      else if (typeName == "野菜") ChicagoStyleVegetablePizza
      else ChicagoStyleClamPizza
    }
  }


  // 製品クラス
  // 抽象製品クラス
  abstract class Pizza(name: String, dough: String, sauce: String, toppings: List[String]) {
    def prepare: Unit = {
      println(s"${name}を下準備")
      println("生地をこねる")
      println("ソースを追加")
      println(s"トッピングを追加: ${toppings.mkString(",")}")
    }

    def bake = println("180度で25分間焼く")

    def cut = println("ピザを扇型にカットする")

    def box = println("PizzaStoreの箱にピザを入れる")

    def getName = name
  }
  
  // 具象製品クラス
  object NYStyleCheesePizza extends Pizza("ニューヨークスタイルのソース&チーズピザ", "薄いクラスト生地", "マリナラソース", List("すりおろしたレッジャーノチーズ"))
  object NYStyleVegetablePizza extends Pizza("ニューヨークスタイルの野菜ピザ", "dummy", "dummy", List.empty)
  object NYStyleClamPizza extends Pizza("ニューヨークスタイルのあさりピザ", "dummy", "dummy", List.empty)
  object ChicagoStyleCheesePizza extends Pizza("シカゴスタイルのチーズピザ", "dummy", "dummy", List.empty) {
    override def cut = println("ピザを四角形にカットする")
  }
  object ChicagoStyleVegetablePizza extends Pizza("シカゴスタイルの野菜ピザ", "dummy", "dummy", List.empty)
  object ChicagoStyleClamPizza extends Pizza("しかごスタイルのあさりピザ", "dummy", "dummy", List.empty)



  // 全ての食材を作成するファクトリのインタフェースを定義。traitではなくabstract classでもよいのでは？という気持ちがある。
  trait PizzaIngredientFactory {
    def createDough: Dough
    def createSauce: Sauce
    def createCheese: Cheese
    def createVeggies: List[Veggie]
    def createPepperoni: Pepperoni
    def createClam: Clams
  }
  // ニューヨーク食材ファクトリ
  class NYPizzaIngredientFactory extends PizzaIngredientFactory {
    // 各食材のニューヨーク版を作成
    def createDough = ThinCrustDough
    def createSauce = MarianaraSauce
    def createCheese = ReggianoCheese
    def createVeggies = List(Garlic, Onion, Mushroom, RedPepper)
    def createPepperoni = SlicedPepperoni
    def createClam = FreshClams
  }
  // シカゴ食材ファクトリ
  class ChicagoPizzaIngredientFactory extends PizzaIngredientFactory {
    // 各食材のニューヨーク版を作成
    def createDough = ThickCrustDough
    def createSauce = PlumTomatoSauce
    def createCheese = MozzarellaCheese
    def createVeggies = List(BlackOlives, Spinach, Eggplant)
    def createPepperoni = SlicedPepperoni
    def createClam = FrozenClams
  }

  abstract class Dough
  object ThinCrustDough extends Dough
  object ThickCrustDough extends Dough
  abstract class Sauce
  object MarianaraSauce extends Sauce
  object PlumTomatoSauce extends Sauce
  abstract class Cheese
  object ReggianoCheese extends Cheese
  object MozzarellaCheese extends Cheese
  abstract class Veggie
  object Garlic extends Veggie
  object Onion extends Veggie
  object Mushroom extends Veggie
  object RedPepper extends Veggie
  object BlackOlives extends Veggie
  object Spinach extends Veggie
  object Eggplant extends Veggie
  abstract class Pepperoni
  object SlicedPepperoni extends Pepperoni
  abstract class Clams
  object FreshClams extends Clams
  object FrozenClams extends Clams




  val nyStore = new NYPizzaStore
  val chicagoStore = new ChicagoPizzaStore
  val pizza1 = nyStore.orderPizza("チーズ")
  println(s"完成しました！${pizza1.getName}")
  println("------------------------------------")
  val pizza2 = chicagoStore.orderPizza("チーズ")
  println(s"完成しました！${pizza2.getName}")

}

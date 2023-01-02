object FactoryAbstract extends App {
  // abstract factory

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
    def createPizza(typeName: String): Pizza = {
      val ingredientFactory = new NYPizzaIngredientFactory
      if (typeName == "チーズ") {
        new CheesePizza(ingredientFactory)
      } else {
        new VeggiePizza(ingredientFactory)
      }
    }
}
  //  class ChicagoPizzaStore extends PizzaStore {
  //    def createPizza(typeName: String) = {
  //      if (typeName == "チーズ") ChicagoStyleCheesePizza
  //      else if (typeName == "野菜") ChicagoStyleVegetablePizza
  //      else ChicagoStyleClamPizza
  //    }
  //  }


  // 製品クラス
  // 抽象製品クラス
  abstract class Pizza {
    val name: String
    // 下準備で使用する一連の食材を保持
    val dough: Dough
    val sauce: Sauce
    val veggies: List[Veggie]
    val cheese: Cheese
    val clam: Clams

    def prepare: Unit

    def bake = println("180度で25分間焼く")

    def cut = println("ピザを扇型にカットする")

    def box = println("PizzaStoreの箱にピザを入れる")

    def getName = name
  }

  class CheesePizza(ingredientFactory: PizzaIngredientFactory) extends Pizza {
      val name = "チーズpizza"
      val dough = ingredientFactory.createDough
      val sauce = ingredientFactory.createSauce
      val cheese = ingredientFactory.createCheese
      val veggies = List.empty
      val clam = ingredientFactory.createClam

      def prepare = println(s"${name}を下準備")
  }
  class VeggiePizza(ingredientFactory: PizzaIngredientFactory) extends Pizza {
      val name = "野菜pizza"
      val dough = ingredientFactory.createDough
      val sauce = ingredientFactory.createSauce
      val cheese = ingredientFactory.createCheese
      val veggies = List.empty
      val clam = ingredientFactory.createClam

      def prepare = println(s"${name}を下準備")
  }


  // 全ての食材を作成するファクトリのインタフェースを定義。traitではなくabstract classでもよいのでは？という気持ちがある。
  // 一連の製品を作成するための抽象型。この抽象型のサブクラスは、一連の製品の作成方法を定義する。
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


  val store = new NYPizzaStore
  val pizza = store.orderPizza("チーズ")
  println(s"${pizza.name}")
}

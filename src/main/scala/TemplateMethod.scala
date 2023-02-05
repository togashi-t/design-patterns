object TemplateMethod extends App {

  class Coffee_ {
    def prepareRecipe = {
      boilWater
      brewCoffeeGrinds
      pourInCup
      addSugarAndMilk
    }

    def boilWater = println("お湯を沸かす")
    def brewCoffeeGrinds = println("フィルタでコーヒーをドリップする")
    def pourInCup = println("カップに注ぐ")
    def addSugarAndMilk = println("砂糖とミルクを追加する")
  }

  class Tea_ {
    def prepareRecipe = {
      boilWater
      steepTeaBag
      addLemon
      pourInCup
    }

    def boilWater = println("お湯を沸かす") // Coffeeと全く同じ部分
    def steepTeaBag = println("紅茶を浸す") // Coffeeとの類似部分
    def pourInCup = println("カップに注ぐ") // Coffeeと全く同じ部分
    def addLemon = println("レモンを追加する") // Coffeeとの類似部分
  }


  // 抽象化した設計に
  trait CaffeineBeverage {
    // これがテンプレートメソッドに該当。テンプレートメソッドはアルゴリズムの手順を定義し、サブクラスが1つ以上の手順の実装を提供できるようにする。
    final def prepareRecipe = {
      boilWater
      brew
      pourInCup
      if (customerWantsCondiments) addCondiments
    }

    // 抽象部分。サブクラスでの実装に委ねる部分。
    def brew: Unit
    def addCondiments: Unit
    // 具象部分。
    def boilWater = println("お湯を沸かす")
    def pourInCup = println("カップに注ぐ")
    // hook。アルゴリズムの一部が省略可能な場合には、その部分にこのようにフックを使う。抽象クラスのアルゴリズムの分岐等を決定する能力をサブクラスに与えるもの。
    def customerWantsCondiments = true
  }

  class Coffee extends CaffeineBeverage {
    def brew = println("フィルタでコーヒーをドリップする")
    def addCondiments = println("砂糖とミルクを追加する")
  }

  class Tea extends CaffeineBeverage {
    def brew = println("紅茶を浸す")
    def addCondiments = println("レモンを追加する")
  }





  // sortの既存テンプレートメソッドを使用する例
  // ソートするオブジェクトの具象比較方法(compareTo)を定義すればよい
  case class Duck(name: String, weight: Int) extends Comparable[Duck] {
    def compareTo(otherDuck: Duck): Int = {
      if (weight < otherDuck.weight) {
        -1
      } else if (weight == otherDuck.weight) {
        0
      } else {
        1
      }
    }
  }

  val duckArray = Array(
    new Duck("ダフィー", 8),
    new Duck("デューイ", 2),
    new Duck("ハワード", 7),
    new Duck("ルーイ", 2),
    new Duck("ドナルド", 10),
    new Duck("ヒューイ", 2),
  )
  println("ソート前-----")
  duckArray.foreach(println)
  println(duckArray)
  println("ソート後-----")
  duckArray.sorted.foreach(println)
  
}

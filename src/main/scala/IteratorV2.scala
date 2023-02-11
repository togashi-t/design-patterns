import java.util

object IteratorV2 extends App {
  // 性質上、全体的にmutableな書き方になるのは仕方がない
  // IteratorV1をjava.util.Iteratorを使用することで整理
  // なお、ArrayList（パンケーキで使用）にはイテレータを返すiteratorメソッドがあるので、専用のイテレータを定義する必要がない。

  class MenuItem(name: String, description: String, vegetarian: Boolean, price: Double) {
    def getName = name
    def getPrice = price
    def isVegetarian = vegetarian
  }

  // WaitressとMenuの具象クラスとの依存関係を減らすためにこれを用いる。実装に対してではなくインターフェースに対してプログラミングする。
  // Menuの具象クラスは、イテレータを生成する責務を負う。
  trait Menu {
    def createIterator: java.util.Iterator[MenuItem]
  }

  // Dinerについて
  // Iterator。ArrayListとは異なりArrayにはイテレータを返すメソッドがないので、このように実装が必要。
  class DinerMenuIterator(items: Array[MenuItem]) extends java.util.Iterator[MenuItem] {
    // varを使わざるを得ない
    var position = 0

    def next: MenuItem = {
      val menuItem = items(position)
      position = position + 1
      menuItem
    }

    def hasNext: Boolean = position + 1 <= items.length
  }

  // Menuの具象クラス
  class DinerMenu extends Menu {
    // コンストラクタ
    val MAX_ITEMS = 6
    var numberOfItems = 0
    val menuItems = new Array[MenuItem](MAX_ITEMS) // 格納できる最大個数を指定して初期化
    addItem("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99)
    addItem("BLT",      "Bacon with lettuce & tomato on whole wheat", false, 2.99)
    addItem("Soup of the day",      "A bowl of the soup of the day, with a side of potato salad", false,      3.29)
    addItem("Hot Dog",      "A hot dog, with saurkraut, relish, onions, topped with cheese", false,      3.05)
    addItem("Steamed Veggies and Brown Rice",      "Steamed vegetables over brown rice", true,      3.99)
    addItem("Pasta",      "Spaghetti with marinara sauce, and a slice of sourdough bread", true, 3.89)

    def addItem(name: String, description: String, vegetarian: Boolean, price: Double): Unit = {
      if (numberOfItems >= MAX_ITEMS) {
        println("メニューはいっぱいです！メニューに項目を追加できません。")
      } else {
        menuItems(numberOfItems) = new MenuItem(name, description, vegetarian, price)
        numberOfItems = numberOfItems + 1
      }
    }

    // Iteratorインタフェースを返している。クライアントはDinerMenu内におけるmenuItemsの保持方法やDinerMenuIteratorの実装方法を知る必要はない。
    // メニューうちの項目にアクセスするには、イテレータを使うだけ。
    def createIterator = new DinerMenuIterator(menuItems)
  }

  // Menuの具象クラス
  // Pancakeについて。java.util.Iteratorを使用するので、Iteratorの専用クラスの定義が不要になった。
  class PancakeHouseMenu extends Menu {
    import java.util.Iterator

    val menuItems = new util.ArrayList[MenuItem]
    addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs and toast", true, 2.99)
    addItem("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false,   2.99)
    addItem("Blueberry Pancakes", "Pancakes made with fresh blueberries, and blueberry syrup",true, 3.49)
    addItem("Waffles", "Waffles with your choice of blueberries or strawberries", true, 3.59)

    def addItem(name: String, description: String, vegetarian: Boolean, price: Double): Unit =
      menuItems.add(new MenuItem(name, description, vegetarian, price))

    def createIterator: java.util.Iterator[MenuItem] = menuItems.iterator // ArrayListにはイテレータを返すiteratorメソッドがある
  }


  // WaitressはMenuとIteratorだけに気を配ればよくなっている
  class Waitress(pancakeHouseMenu: Menu, dinerMenu: Menu) {
    import java.util.Iterator
    def printMenu: Unit = {
      println("メニュー\n---\n朝食")
      printMenu(pancakeHouseMenu.createIterator)
      println("\n昼食")
      printMenu(dinerMenu.createIterator)
    }

    def printMenu(iterator: java.util.Iterator[MenuItem]): Unit = {
      while (iterator.hasNext) {
        val menuItem = iterator.next
        print(menuItem.getName + "、")
        print(menuItem.getPrice + " -- ")
        println(menuItem.getName)
      }
    }
  }



  // 試してみる
  val pancakeHouseMenu = new PancakeHouseMenu
  val dinerMenu = new DinerMenu
  val waitress = new Waitress(pancakeHouseMenu, dinerMenu)
  waitress.printMenu

}

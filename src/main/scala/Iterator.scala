import java.util

object Iterator {
  // 性質上、全体的にmutableな書き方になるのは仕方がない

  class MenuItem(name: String, description: String, vegetarian: Boolean, price: Double) {
    def getName = name
    def getPrice = price
    def isVegetarian = vegetarian
  }


  trait Iterator {
    def hasNext: Boolean
    def next: MenuItem
  }

  // Dinerについて
  class DinerMenuIterator(items: Array[MenuItem]) extends Iterator {
    // varを使わざるを得ない
    var position = 0

    def next: MenuItem = {
      val menuItem = items(position)
      position = position + 1
      menuItem
    }

    def hasNext: Boolean = position + 1 < items.length
  }

  class DinerMenu {
    // コンストラクタ
    val MAX_ITEMS = 6
    var numberOfItems = 0
    val menuItems = new Array[MenuItem](MAX_ITEMS) // 格納できる最大個数を指定して初期化
    addItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99)
    addItem("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false, 2.99)
    addItem("Blueberry Pancakes", "Pancakes made with fresh blueberries, and blueberry syrup", true, 3.49)
    addItem("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59)

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


  // Pancakeについて
  class PancakeHouseIterator(items: util.ArrayList[MenuItem]) extends Iterator {
    // varを使わざるを得ない
    var position = 0

    def next: MenuItem = {
      val menuItem = items.get(position)
      position = position + 1
      menuItem
    }

    def hasNext: Boolean = position + 1 < items.size
  }

  class PancakeHouseMenu {
    val menuItems = new util.ArrayList[MenuItem]

    def addItem(name: String, description: String, vegetarian: Boolean, price: Double): Unit =
      menuItems.add(new MenuItem(name, description, vegetarian, price))

    def createIterator = new PancakeHouseIterator(menuItems)
  }



}

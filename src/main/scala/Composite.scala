import java.util

object Composite extends App {

  // 全てのメソッドのデフォルト実装を用意
  abstract class MenuComponent {
    // コンポジット用のメソッド
    def add(menuComponent: MenuComponent): Unit = throw new UnsupportedOperationException
    def remove(menuComponent: MenuComponent): Unit = throw new UnsupportedOperationException
    def getChild(i: Int): MenuComponent = throw new UnsupportedOperationException

    // MenuItem用のメソッド
    def getName: String = throw new UnsupportedOperationException
    def getDescription: String = throw new UnsupportedOperationException
    def getPrice: Double = throw new UnsupportedOperationException
    def isVegetarian: Boolean = throw new UnsupportedOperationException

    // 両方（コンポジットおよびMenuItem）用のメソッド
    def print: Unit = throw new UnsupportedOperationException
  }

  // 具象クラスを実装
  class MenuItem(name: String, description: String, vegetarian: Boolean, price: Double) extends MenuComponent {
    override def getName: String = name
    override def getDescription: String = description
    override def getPrice: Double = price
    override def isVegetarian: Boolean = vegetarian

    override def print: Unit = {
      Predef.print(s" ${getName}")
      if (isVegetarian) Predef.print("(v")
      println(s"、${getPrice}")
      println(s"   -- ${getDescription}")
    }
  }

  // 具象クラスを実装。従前はMenu毎に異なるクラスを作成していたが、ここではそうではなく名前と説明を与えている。
  class Menu(name: String, description: String) extends MenuComponent {
    val menuComponents = new util.ArrayList[MenuComponent]

    override def add(menuComponent: MenuComponent) = menuComponents.add(menuComponent)

    override def remove(menuComponent: MenuComponent) = menuComponents.remove(menuComponent)

    override def getChild(i: Int) = menuComponents.get(i)

    override def getName: String = name

    override def getDescription: String = description

    override def print: Unit = {
      // Menuに関する情報を出力
      Predef.print(s"\n${getName}")
      println(s"、${getDescription}")
      println("----------------------")
      // 加えて、Menuの全てのコンポーネントを出力。
      // コンポーネントはMenuの場合もあればMenuItemの場合もあるが、いずれの場合もprintを実装しているので、その処理は各コンポーネントに任せる。
      menuComponents.forEach(_.print)
    }
  }

  // 最上位のメニューコンポーネント（他の全てのメニューを含むコンポーネント）を渡している
  class Waitress(allMenus: MenuComponent) {
    def printMenu: Unit = allMenus.print
  }



  // 試してみる
  // 各メニュー
  val pancakeHouseMenu = new Menu("パンケーキハウスメニュー", "朝食")
  val dinerMenu = new Menu("食堂メニュー", "昼食")
  val cafeMenu = new Menu("カフェメニュー", "夕食")
  val dessertMenu = new Menu("デザートメニュー", "もちろんデザート")
  // 最上位のメニュー
  val allMenus = new Menu("すべてのメニュー", "すべてを統合したメニュー")
  // 各メニューを追加
  allMenus.add(pancakeHouseMenu)
  allMenus.add(dinerMenu)
  allMenus.add(cafeMenu)
  // 各メニューにメニューアイテムを追加するのは面倒なので、昼食メニューにだけ追加
  dinerMenu.add(new MenuItem("パスタ", "マリナラソーススパゲティとサワードウパン", true, 3.89))
  // 昼食メニューにデザートメニューを追加
  dinerMenu.add(dessertMenu)
  // デザートメニューにメニューアイテムを追加
  dessertMenu.add(new MenuItem("アップルパイ", "バニラアイスクリームをのせたフレーク状生地のアップルパイ", true, 1.59))

  new Waitress(allMenus).printMenu


}

object Strategy extends App {
  // 変化する部分を取り出してカプセル化する。すると、その部分のコードは他の部分に影響を及ぼさない。

  // 鳴く振る舞い
  trait QuackBehavior {
    def quack: Unit
  }
  class Quack extends QuackBehavior {
    def quack = println("ガーガー")
  }
  class MuteQuack extends QuackBehavior {
    def quack = println("<沈黙>")
  }
  class Squeak extends QuackBehavior {
    def quack = println("キューキュー")
  }

  // 飛ぶ振る舞い
  trait FlyBehavior {
    def fly: Unit
  }
  class FlyWithWings extends FlyBehavior {
    def fly = println("飛んでいます")
  }
  class FlyNoWay extends FlyBehavior {
    def fly = println("飛べません")
  }

  // 特定の種類の振る舞いで初期化することで、振る舞いを実行時に変更できるようにしている。
  abstract class Duck {
    // QuackBehaviorのtraitを実装したものへの参照を持つ
    val quackBehavior: QuackBehavior
    val flyBehavior: FlyBehavior
    // 鳴くふるまい自体を処理するのではなく、その振る舞いをquackBehaviorが参照するオブジェクトに委譲する
    def performQuack = quackBehavior.quack
    def performFly = flyBehavior.fly
  }

  class MallardDuck extends Duck {
    // performQuackが呼び出されると、鳴く振る舞いの責務をQuackオブジェクトに委譲し、実際の鳴く振る舞いを取得する。
    val quackBehavior = new Quack
    val flyBehavior = new FlyWithWings
  }



  // Duckの振る舞いは別のクラス（特定の振る舞いのtraitを実装したクラス）に配置している。
  // そのため、Duckのサブクラスはそれぞれの振る舞いの実装の詳細を知る必要が無い。

  val md = new MallardDuck
  md.performQuack
  md.performFly


  // abstract class Duck(val quackBehavior: QuackBehavior, val flyBehavior: FlyBehavior) {}
  // class MallardDuck extends Duck(new Quack, new FlyWithWings)
  // というように、コンストラクタを使用した書き方にするのがおそらく一般的
}

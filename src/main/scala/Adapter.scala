object Adapter extends App {
  // カモ
  trait Duck {
    def quack: Unit
    def fly: Unit
  }

  class MallardDuck extends Duck {
    def quack = println("ガーガー")
    def fly = println("飛んでいます")
  }


  // 新顔の七面鳥について
  // Duckとは異なるので以下のように新たに定義するわけだが、当然異なるインターフェースを持つことになるのでそのまま（カモと同様に）は使えない。
  trait Turkey {
    def gobble: Unit
    def fly: Unit
  }

  class WildTurkey extends Turkey {
    def gobble = println("ゴロゴロ")
    def fly = println("短い距離を飛んでいます")
  }

  // そこでアダプタを書く。TurkeyをDuckとしてクライアントが扱えるようにするもの。
  class TurkeyAdapter(turkey: Turkey) extends Duck {
    def quack = turkey.gobble
    def fly = (1 to 5).foreach(_ => turkey.fly)
  }

  // Duckを引数として、鳴く、飛ぶをするもの。引数のDuckには、TurkeyAdapterの使用によりDuckになったTurkeyも入ることになる。
  def testDuck(duck: Duck) = {
    duck.quack
    duck.fly
  }


  // 試しに使ってみる
  val duck = new MallardDuck
  val turkey = new WildTurkey
  val turkeyAdapter = new TurkeyAdapter(turkey)

  println("Turkeyの出力-----")
  turkey.gobble
  turkey.fly

  println("Duckの出力-----")
  testDuck(duck)

  println("TurkeyAdapterの出力-----")
  testDuck(turkeyAdapter)





  // EnumerationをIteratorに適合させる
  class EnumerationIterator[T](enumeration: java.util.Enumeration[T]) extends Iterator[T] {
    def hasNext = enumeration.hasMoreElements
    def next = enumeration.nextElement
    def remove = throw new UnsupportedOperationException
  }

  
}

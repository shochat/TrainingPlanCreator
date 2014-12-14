package tpcCreatorTests
/**
 * Created by ilan.s on 7/13/2014.
 */
import org.scalatest.FlatSpec
class CreatorTests extends FlatSpec{

  "Something to test" should "return a value" in  {
    assert("""bla-bal""".length == 7)
  }

  "second test" should "return something else" in {
    assert("ilan".isEmpty == false)
  }
}

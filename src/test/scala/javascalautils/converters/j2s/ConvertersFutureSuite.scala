/**
 *  Copyright 2015 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package javascalautils.converters.j2s

import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import javascalautils.concurrent.{ Future => JFuture}
import javascalautils.converters.j2s.Converters._
import javascalautils.ThrowableFunction0

/**
 * Test suite for Converters Future conversions.
 * @author Peter Nerg
 */
class ConvertersFutureSuite extends FunSuite with ScalaFutures {
  val expected = "The Future is here"

  test("Test asScalaFuture with completed Java Future") {
    //create a completed Future
    val jfuture = JFuture.successful(expected)
    val future = asScalaFuture(jfuture)

    //should be completed
    assert(future.isCompleted)

    assert(future.futureValue === expected)
  }

  test("Test asScalaFuture with not yet completed Java Future") {
    //create a completed Future
    val jfuture = JFuture.apply(new ThrowableFunction0[String]() {
      def apply() = {
        //simulates some execution time
        Thread.sleep(50)
        expected
      }
    })
    val future = asScalaFuture(jfuture)
  
    assert(future.futureValue === expected)
  }
}
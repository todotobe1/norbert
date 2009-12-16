/*
 * Copyright 2009 LinkedIn, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.linkedin.norbert.cluster

import java.net.{InetSocketAddress, InetAddress}
import org.specs.SpecificationWithJUnit
import com.linkedin.norbert.protos.NorbertProtos

class NodeSpec extends SpecificationWithJUnit {
  "Node" should {
    "serialize into the correct format" in {
      val builder = NorbertProtos.Node.newBuilder
      builder.setId(1)
      builder.setHostname("localhost")
      builder.setPort(31313)
      builder.addPartitions(0).addPartitions(1)
      val bytes = builder.build.toByteArray

      Node.nodeToByteArray(Node(1, new InetSocketAddress("localhost", 31313), Array(0, 1), false)) must containInOrder(bytes)
    }

    "deserialize into the corrent Node" in {
      val builder = NorbertProtos.Node.newBuilder
      builder.setId(1)
      builder.setHostname("localhost")
      builder.setPort(31313)
      builder.addPartitions(0).addPartitions(1)
      val bytes = builder.build.toByteArray

      val node = Node(1, new InetSocketAddress("localhost", 31313), Array(0, 1), true)
      Node(1, bytes, true) must be_==(node)
    }

    "have a sane equals method" in {
      val ia = new InetSocketAddress("localhost", 31313)
      val node1 = Node(1, ia, Array(0, 1), true)
      val node2 = Node(1, ia, Array(2, 3), false)
      val node3 = Node(1, ia, Array(4, 5), true)

      // Reflexive
      node1 must be_==(node1)

      // Symmetric
      node1 must be_==(node2)
      node2 must be_==(node1)

      // Transitive
      node1 must be_==(node2)
      node2 must be_==(node3)
      node3 must be_==(node1)

      // Consistent already handled above

      // Handles null
      node1 must be_!=(null)

      // Hashcode
      node1.hashCode must be_==(node2.hashCode)
    }

    "be equal to another node if they have the same id and address" in {
      val ia = InetAddress.getByName("localhost")
      val node1 = Node(1, new InetSocketAddress(ia, 31313), Array(0, 1), true)
      val node2 = Node(1, new InetSocketAddress(ia, 31313), Array(1, 2), false)
      node1 must be_==(node2)
    }

    "not be equal to another node if they have a different id" in {
      val ia = InetAddress.getByName("localhost")
      val node1 = Node(1, new InetSocketAddress(ia, 31313), Array(0, 1), true)
      val node2 = Node(2, new InetSocketAddress(ia, 31313), Array(0, 1), true)
      node1 must be_!=(node2)      
    }

    "not be equal to another node if they have a different address" in {
      val ia = InetAddress.getByName("localhost")
      val node1 = Node(1, new InetSocketAddress(ia, 31313), Array(0, 1), true)
      val node2 = Node(1, new InetSocketAddress(ia, 16161), Array(0, 1), true)
      node1 must be_!=(node2)
    }
  }
}

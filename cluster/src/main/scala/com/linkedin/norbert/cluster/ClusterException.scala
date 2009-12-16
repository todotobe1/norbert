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

import com.linkedin.norbert.NorbertException

class ClusterException(message: String, cause: Throwable) extends NorbertException(message, cause) {
  def this() = this(null, null)
  def this(message: String) = this(message, null)
  def this(cause: Throwable) = this(cause.getMessage, cause)
}

class ClusterDisconnectedException(message: String) extends ClusterException(message)

class ClusterShutdownException extends ClusterException

class InvalidNodeException(message: String, cause: Throwable) extends ClusterException(message) {
  def this(message: String) = this(message, null)
}

class InvalidClusterException(message: String) extends ClusterException(message)

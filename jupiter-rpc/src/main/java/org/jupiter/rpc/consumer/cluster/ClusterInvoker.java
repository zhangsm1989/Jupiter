/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jupiter.rpc.consumer.cluster;

import org.jupiter.rpc.JClient;
import org.jupiter.rpc.consumer.dispatcher.Dispatcher;

/**
 * jupiter
 * org.jupiter.rpc.consumer.cluster
 *
 * @author jiachun.fjc
 */
public class ClusterInvoker {

    public enum Strategy {
        FAIL_FAST, // 快速失败
        FAIL_OVER, // 失败重试
        FAIL_SAFE; // 失败安全

        public static Strategy parse(String name) {
            for (Strategy s : values()) {
                if (s.name().equalsIgnoreCase(name)) {
                    return s;
                }
            }
            return null;
        }
    }

    protected final JClient client;
    protected final Dispatcher dispatcher;

    public ClusterInvoker(JClient client, Dispatcher dispatcher) {
        this.client = client;
        this.dispatcher = dispatcher;
    }

    /**
     * The default impl only returns a {@link org.jupiter.rpc.consumer.future.InvokeFuture}.
     */
    public Object invoke(String methodName, Object[] args, Class<?> returnType) throws Exception {
        return dispatcher.dispatch(client, methodName, args, returnType);
    }
}
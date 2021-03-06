/*
 * Copyright 2016 The original authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.sundr.dsl.internal.processor;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.concurrent.atomic.AtomicReference;

public class DslContextManager {

    private DslContextManager() {
    }

    private static final AtomicReference<DslContext> context = new AtomicReference<DslContext>();

    public static DslContext create(Elements elements, Types types) {
        DslContext ctx = new DslContext(elements, types);
        if (context.compareAndSet(null, ctx)) {
            return ctx;
        } else {
            DslContext existing = context.get();
            return existing;
        }
    }

    public static synchronized DslContext getContext() {
        if (context.get() == null) {
            throw new IllegalStateException("Dsl context not available.");
        }
        return context.get();
    }
}

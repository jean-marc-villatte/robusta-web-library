/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.rest.client.implementation;

import com.robustaweb.library.rest.client.AsynchronousRestClient;

/**
 *
 * @author n.zozol
 */
public class DataCallback<D> extends SimpleCallback {

    D datas;

    public DataCallback(AsynchronousRestClient client) {
        super(client);
    }

    /**
     * Returns datas set during Callback's work
     * @return
     */
    public D getDatas() {
        return datas;
    }

    /**
     * Set Domain datas. This method is designed to be used while the callback decodes request's result.
     * @param datas
     */
    public void setDatas(D datas) {
        this.datas = datas;
    }
}

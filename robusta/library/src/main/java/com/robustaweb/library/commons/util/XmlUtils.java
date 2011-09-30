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
package com.robustaweb.library.commons.util;

import com.robustaweb.library.rest.resource.Resource;

/**
 * @todo3 : Move into a RepresentationUtils class
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class XmlUtils {



    /**
     * Easily build a Very Simple XML string form nodeNames with values, with the ability to choose the root nodeName
     * @param rootName name of the root element
     * @param elements alternate nodeName and nodeValue
     * @throws IllegalArgumentException if number of params is not pair.
     * @return an Xml String
     */
    public static String build(String headers, String rootName, String... elements) {
        CoupleList<String, String> clist = CoupleList.<String, String>build((Object[])elements);


        StringBuilder xml = new StringBuilder();
        if (headers != null){
               xml.append(headers);
        }
        xml.append("<" + rootName + ">").append("\n");
        Couple c;
        String nodeName, nodeValue;
        for (int i = 0; i < clist.size(); i++) {
            c = clist.get(i);

            if (c.getLeft() == null) {
                throw new IllegalArgumentException("One nodeName is null");
            }
            nodeName = c.getLeft().toString();

            if (c.getRight() == null) {
                nodeValue = "";
            } else {
                nodeValue = c.getRight().toString();
            }
            xml.append("   <").append(nodeName).append(">").append(nodeValue).append("</").append(nodeName).append(">\n");
        }
        xml.append("</").append(rootName).append(">");

        return xml.toString();
    }


     /**
     * Easily build a Very Simple XML string form nodeNames with values, with the ability to choose the root nodeName
     * @param headers headers of the Xml String, or null of none is needed
     * @param resource to be represented
     * @return an Xml String
     */
    public static String build(String headers, Resource resource){
        return build(headers, resource.getPrefix(), (String[]) resource.serialize().stringify().flat());
    }

}

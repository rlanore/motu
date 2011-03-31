/* 
 * Motu, a high efficient, robust and Standard compliant Web Server for Geographic
 * Data Dissemination.
 *
 * http://cls-motu.sourceforge.net/
 *
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites) - 
 * http://www.cls.fr - and  Contributors
 *
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * Code from http://weblogs.java.net/blog/emcmanus/archive/2006/10/build_your_own.html
 * 
 * MBeanInvocationHandler.java
 *                ,
 * Copyright 2006 Eamonn McManus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.cls.atoll.motu.library.misc.mxbean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MBeanInvocationHandler implements InvocationHandler {
    public MBeanInvocationHandler(Object wrapped) {
        this.wrapped = wrapped;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
    throws Throwable {
        Class<?> wrappedClass = wrapped.getClass();
        Method methodInWrapped =
            wrappedClass.getMethod(method.getName(), method.getParameterTypes());
        try {
            return methodInWrapped.invoke(wrapped, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private final Object wrapped;
}

/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mybatis.guice.CustomLongTypeHandler;
import org.mybatis.guice.CustomType;
import org.mybatis.guice.generictypehandler.CustomObject;
import org.mybatis.guice.generictypehandler.GenericCustomObjectTypeHandler;

public class TypeHandlerProviderTest {
  private TypeHandlerProvider<CustomLongTypeHandler, CustomType> typeHandlerProvider;
  private TypeHandlerProvider<GenericCustomObjectTypeHandler<CustomObject>, CustomObject> genericTypeHandlerProvider;
  @Mock
  private Injector injector;
  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void beforeTest() {
    typeHandlerProvider = new TypeHandlerProvider<CustomLongTypeHandler, CustomType>(injector,
        CustomLongTypeHandler.class, null);
    genericTypeHandlerProvider = new TypeHandlerProvider<GenericCustomObjectTypeHandler<CustomObject>, CustomObject>(
        injector, new TypeLiteral<GenericCustomObjectTypeHandler<CustomObject>>() {
        }, CustomObject.class);
  }

  @Test
  public void get() {
    CustomLongTypeHandler typeHandler = typeHandlerProvider.get();
    assertNotNull(typeHandler);
    verify(injector).injectMembers(typeHandler);
    GenericCustomObjectTypeHandler<CustomObject> genericTypeHandler = genericTypeHandlerProvider.get();
    assertNotNull(genericTypeHandler);
    verify(injector).injectMembers(genericTypeHandler);
    assertEquals(CustomObject.class, genericTypeHandler.getType());
  }
}

/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.connection;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.util.ClassUtils;

/**
 * Represents a data type returned from Redis, currently used to denote the expected return type of Redis scripting
 * commands
 *
 * @author Jennifer Hickey
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public enum ReturnType {

	/**
	 * Returned as Boolean
	 */
	BOOLEAN,

	/**
	 * Returned as {@link Long}
	 */
	INTEGER,

	/**
	 * Returned as {@link List<Object>}
	 */
	MULTI,

	/**
	 * Returned as {@code byte[]}
	 */
	STATUS,

	/**
	 * Returned as {@code byte[]}
	 */
	VALUE;

	/**
	 * @param javaType can be {@literal null} which translates to {@link ReturnType#STATUS}.
	 * @return never {@literal null}.
	 */
	public static ReturnType fromJavaType(@Nullable Class<?> javaType) {

		if (javaType == null) {
			return ReturnType.STATUS;
		}

		if (ClassUtils.isAssignable(List.class, javaType)) {
			return ReturnType.MULTI;
		}

		if (ClassUtils.isAssignable(Boolean.class, javaType)) {
			return ReturnType.BOOLEAN;
		}

		if (ClassUtils.isAssignable(Double.class, javaType) || ClassUtils.isAssignable(Float.class, javaType)) {
			return ReturnType.VALUE;
		}

		if (ClassUtils.isAssignable(Number.class, javaType)) {
			return ReturnType.INTEGER;
		}

		return ReturnType.VALUE;
	}
}

/*
 * Copyright 2025 the original author or authors.
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

import java.util.Objects;

import org.springframework.lang.Contract;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * @author Christoph Strobl
 * @since 3.5
 */
public interface Hash {

	class FieldExpirationOptions {

		private static final FieldExpirationOptions NONE = new FieldExpirationOptions(null);
		private @Nullable Condition condition;

		FieldExpirationOptions(@Nullable Condition condition) {
			this.condition = condition;
		}

		public static FieldExpirationOptions none() {
			return NONE;
		}

		@Contract("_ -> new")
		public static FieldExpireOptionsBuilder builder() {
			return new FieldExpireOptionsBuilder();
		}

		public @Nullable Condition getCondition() {
			return condition;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			FieldExpirationOptions that = (FieldExpirationOptions) o;
			return ObjectUtils.nullSafeEquals(this.condition, that.condition);
		}

		@Override
		public int hashCode() {
			return Objects.hash(condition);
		}

		public static class FieldExpireOptionsBuilder {

			@Nullable Condition condition;

			@Contract("_ -> this")
			public FieldExpireOptionsBuilder nx() {
				this.condition = Condition.NX;
				return this;
			}

			@Contract("_ -> this")
			public FieldExpireOptionsBuilder xx() {
				this.condition = Condition.XX;
				return this;
			}

			@Contract("_ -> this")
			public FieldExpireOptionsBuilder gt() {
				this.condition = Condition.GT;
				return this;
			}

			@Contract("_ -> this")
			public FieldExpireOptionsBuilder lt() {
				this.condition = Condition.LT;
				return this;
			}

			@Contract("_ -> !null")
			public FieldExpirationOptions build() {
				return condition == null ? NONE : new FieldExpirationOptions(condition);
			}
		}

		public enum Condition {

			/** Set expiration only when the field has no expiration. */
			NX,
			/** Set expiration only when the field has an existing expiration. */
			XX,
			/** Set expiration only when the new expiration is greater than current one. */
			GT,
			/** Set expiration only when the new expiration is greater than current one. */
			LT
		}
	}
}

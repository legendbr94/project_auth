
package com.develop.project_auth.core.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.hibernate.criterion.MatchMode;

public class CriteriaBuilderUtil {

  public static Predicate like(final CriteriaBuilder builder, final MatchMode matchMode,
      final Path<?> attribute, final String value) {
    return builder.like(builder.trim(builder.lower(builder
            .function("unaccent", (Class) String.class, new Expression[]{(Expression) attribute}))),
        matchMode.toMatchString(StringUtil.unaccent(value.toLowerCase().trim())),
        '\\');
  }
}

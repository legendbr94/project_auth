package com.develop.project_auth.core.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class Paths {

  public static final String PUBLIC_USERS = "public/users";

  public static final String USERS = "users";

  public static final String GROUPS = "groups";

  public static final String GROUPS_PERMISSIONS = "groups/{id}/permissions";

  public static final String USERS_GROUPS = "users/{id}/groups";

}

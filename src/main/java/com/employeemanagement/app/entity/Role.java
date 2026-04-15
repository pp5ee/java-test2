package com.employeemanagement.app.entity;

/**
 * Organisational roles that reflect the company hierarchy.
 *
 * <p>Roles are ordered by authority level: higher ordinal values indicate
 * greater authority. Use {@link #canManage(Role)} to determine whether one
 * role has authority over another.
 */
public enum Role {

    /** System administrator — can manage all users and settings. */
    ADMIN,

    /** Chief Executive Officer — top of the business hierarchy. */
    CEO,

    /** Chief Technology Officer. */
    CTO,

    /** Department manager / team lead. */
    MANAGER,

    /** Regular software developer / programmer. */
    PROGRAMMER,

    /** General employee with no management responsibilities. */
    EMPLOYEE;

    /**
     * Returns {@code true} when this role has the authority to manage
     * (assign tasks to, remove, etc.) the given {@code other} role.
     *
     * <p>ADMIN can manage everyone. Otherwise a role can only manage roles
     * with a higher ordinal value (lower in the hierarchy).
     */
    public boolean canManage(Role other) {
        if (this == ADMIN) return true;
        return this.ordinal() < other.ordinal();
    }
}

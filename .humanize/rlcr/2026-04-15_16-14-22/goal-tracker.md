# Goal Tracker

<!--
This file tracks the ultimate goal, acceptance criteria, and plan evolution.
It prevents goal drift by maintaining a persistent anchor across all rounds.

RULES:
- IMMUTABLE SECTION: Do not modify after initialization
- MUTABLE SECTION: Update each round, but document all changes
- Every task must be in one of: Active, Completed, or Deferred
- Deferred items require explicit justification
-->

## IMMUTABLE SECTION
<!-- Do not modify after initialization -->

### Ultimate Goal

Build a Java-based Spring Boot backend application with embedded React frontend for enterprise employee management. The system provides:
- Email-based user registration and login with verification codes
- Role-based user management with organizational hierarchy (CEO, CTO, Programmer, etc.)
- Task Kanban board where managers can assign tasks to subordinates
- Single-port deployment serving both API and frontend
- Swagger API documentation
- MySQL database persistence

## Acceptance Criteria

### Acceptance Criteria
<!-- Each criterion must be independently verifiable -->
<!-- Claude must extract or define these in Round 0 -->


- AC-1: User Registration and Email Verification
  - Positive Tests (expected to PASS):
    - New user can register with valid email address
    - System sends verification code to provided email
    - User can verify email with correct code
    - Verified user can log in successfully
  - Negative Tests (expected to FAIL):
    - Registration with invalid email format is rejected
    - Registration with duplicate email is rejected
    - Login with incorrect password is rejected
    - Unverified email cannot log in

- AC-2: Role-Based Access Control with Organizational Hierarchy
  - Positive Tests:
    - Admin can create users with specific roles (CEO, CTO, Programmer, etc.)
    - Higher-level users can view their subordinates
    - Manager can assign tasks to direct reports
    - Admin can remove/deactivate users
  - Negative Tests:
    - Regular users cannot access admin functions
    - Users cannot assign tasks to their managers
    - Deactivated users cannot log in

- AC-3: Task Kanban Board

---

## MUTABLE SECTION
<!-- Update each round with justification for changes -->

### Plan Version: 1 (Updated: Round 0)

#### Plan Evolution Log
<!-- Document any changes to the plan with justification -->
| Round | Change | Reason | Impact on AC |
|-------|--------|--------|--------------|
| 0 | Initial plan | - | - |

#### Active Tasks
<!-- Map each task to its target Acceptance Criterion and routing tag -->
| Task | Target AC | Status | Tag | Owner | Notes |
|------|-----------|--------|-----|-------|-------|
| task1 | AC-5 | completed | coding | claude | Set up Spring Boot project with Maven, dependencies |
| task2 | AC-5 | completed | coding | claude | Configure MySQL database and JPA entities |
| task3 | AC-1 | in_progress | coding | claude | Implement user registration with email |
| task4 | AC-1, AC-6 | pending | coding | claude | Implement email verification code sending |
| task5 | AC-1 | pending | coding | claude | Implement login/logout functionality |
| task6 | AC-2 | pending | coding | claude | Implement role-based access control |
| task7 | AC-2 | completed | coding | claude | Create organizational hierarchy structure |
| task8 | AC-4 | completed | coding | claude | Set up React frontend build configuration |
| task9 | AC-4 | completed | coding | claude | Integrate React into Spring Boot |
| task10 | AC-3 | pending | coding | claude | Implement task entity and CRUD operations |
| task11 | AC-3 | pending | coding | claude | Implement Kanban board API |
| task12 | AC-3 | completed | coding | claude | Build Kanban board frontend components |
| task13 | AC-7 | completed | coding | claude | Configure Swagger documentation |
| task14 | - | pending | coding | claude | Create README documentation |

### Completed and Verified
<!-- Only move tasks here after Codex verification -->
| AC | Task | Completed Round | Verified Round | Evidence |
|----|------|-----------------|----------------|----------|

### Explicitly Deferred
<!-- Items here require strong justification -->
| Task | Original AC | Deferred Since | Justification | When to Reconsider |
|------|-------------|----------------|---------------|-------------------|

### Open Issues
<!-- Issues discovered during implementation -->
| Issue | Discovered Round | Blocking AC | Resolution Path |
|-------|-----------------|-------------|-----------------|

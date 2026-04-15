# Employee Management System with Task Kanban Board

## Goal Description

Build a Java-based Spring Boot backend application with embedded React frontend for enterprise employee management. The system provides:
- Email-based user registration and login with verification codes
- Role-based user management with organizational hierarchy (CEO, CTO, Programmer, etc.)
- Task Kanban board where managers can assign tasks to subordinates
- Single-port deployment serving both API and frontend
- Swagger API documentation
- MySQL database persistence

## Acceptance Criteria

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
  - Positive Tests:
    - User can view their assigned tasks on Kanban board
    - User can view tasks they created
    - Manager can create and assign tasks to subordinates
    - Tasks can move between columns (To Do, In Progress, Done)
    - Task details show assignee, creator, and status
  - Negative Tests:
    - Users cannot view tasks assigned to other users at same level
    - Unassigned tasks are not visible to random users
    - Task status transitions follow valid workflow

- AC-4: Unified API and Frontend Deployment
  - Positive Tests:
    - Backend API accessible on configured port
    - React frontend served from same port
    - Frontend can communicate with backend API
    - Swagger documentation accessible at /swagger-ui.html
  - Negative Tests:
    - Frontend 404 when backend is not running
    - API calls fail when frontend makes cross-origin requests to wrong port

- AC-5: MySQL Database Integration
  - Positive Tests:
    - Application connects to MySQL database
    - All user data persists across restarts
    - Task data persists across restarts
    - Organizational hierarchy data persists
  - Negative Tests:
    - Application fails to start without MySQL connection
    - Data is lost when database table is truncated

- AC-6: Email Service Configuration
  - Positive Tests:
    - Email service reads configuration from application.properties/yml
    - Verification codes are sent via configured SMTP server
    - Email fails gracefully when SMTP is misconfigured
  - Negative Tests:
    - Application crashes if email configuration is missing
    - Verification codes work without actual email sending (test mode)

- AC-7: Swagger API Documentation
  - Positive Tests:
    - All REST endpoints are documented
    - Request/response schemas are defined
    - Authentication endpoints are documented
  - Negative Tests:
    - Hidden endpoints without documentation are accessible

## Path Boundaries

### Upper Bound (Maximum Acceptable Scope)
The implementation includes complete Spring Boot backend with Spring Security for authentication, JPA/Hibernate for MySQL persistence, Spring Mail for email services, and embedded React frontend served via Spring Boot's resource handlers. Full role-based access control with hierarchical organization structure. Complete Kanban board with task creation, assignment, and status management. Swagger 2/OpenAPI 3 documentation for all endpoints. Production-ready configuration management.

### Lower Bound (Minimum Acceptable Scope)
The implementation includes basic Spring Boot REST API with email-based registration and login. Simple role-based access with fixed roles (Admin, Manager, Employee). Basic Kanban board with three columns (To Do, In Progress, Done). React frontend with minimal components. MySQL schema with basic tables. Email verification code sent via SMTP. Swagger documentation for main endpoints.

### Allowed Choices
- Can use: Spring Boot 2.x or 3.x, Spring Security with JWT or session-based auth, JPA with Hibernate, Spring Data MySQL, Spring Mail, React with Create React App or Vite, embedded React via Maven/Gradle frontend plugin
- Cannot use: Other backend frameworks (not Spring Boot), other databases (not MySQL), separate frontend deployment (must be embedded), no authentication

## Dependencies and Sequence

### Milestones
1. **Project Setup and Core Infrastructure**
   - Initialize Spring Boot project with required dependencies
   - Configure MySQL database connection
   - Set up Spring Security framework
   - Configure Swagger documentation

2. **User Management and Authentication**
   - Implement email registration endpoint
   - Implement email verification flow
   - Implement login/logout functionality
   - Create role and organizational hierarchy entities

3. **Frontend Integration**
   - Set up React frontend build configuration
   - Integrate React with Spring Boot
   - Configure unified port serving

4. **Task Kanban Board**
   - Implement task entity and repository
   - Create task REST API endpoints
   - Build Kanban board frontend components
   - Implement task assignment logic

5. **Final Integration and Testing**
   - End-to-end testing
   - Swagger documentation refinement
   - README documentation

## Task Breakdown

| Task ID | Description | Target AC | Tag (`coding`/`analyze`) | Depends On |
|---------|-------------|-----------|----------------------------|------------|
| task1 | Set up Spring Boot project with Maven/Gradle, dependencies | AC-5 | coding | - |
| task2 | Configure MySQL database and JPA entities | AC-5 | coding | task1 |
| task3 | Implement user registration with email | AC-1 | coding | task2 |
| task4 | Implement email verification code sending | AC-1, AC-6 | coding | task3 |
| task5 | Implement login/logout functionality | AC-1 | coding | task4 |
| task6 | Implement role-based access control | AC-2 | coding | task5 |
| task7 | Create organizational hierarchy structure | AC-2 | coding | task6 |
| task8 | Set up React frontend build configuration | AC-4 | coding | task1 |
| task9 | Integrate React into Spring Boot | AC-4 | coding | task8 |
| task10 | Implement task entity and CRUD operations | AC-3 | coding | task7 |
| task11 | Implement Kanban board API | AC-3 | coding | task10 |
| task12 | Build Kanban board frontend components | AC-3 | coding | task11 |
| task13 | Configure Swagger documentation | AC-7 | coding | task5 |
| task14 | Create README documentation | - | coding | task13 |

## Claude-Codex Deliberation

### Agreements
- Both Claude and Codex agree on the core feature set: email authentication, role-based hierarchy, Kanban board
- Both recognize the need for Swagger documentation
- Both confirm MySQL is the required database per draft specification
- Both acknowledge unified port deployment requirement

### Resolved Disagreements
- None - the draft provides clear direction on core requirements

### Convergence Status
- Final Status: `partially_converged` (direct mode, no convergence loop executed)

## Pending User Decisions

- DEC-1: Role Permission Granularity
  - Claude Position: Use fixed roles (Admin, Manager, Employee) with hierarchical permissions
  - Codex Position: Consider configurable per-role permissions
  - Tradeoff Summary: Fixed roles are simpler to implement and maintain; configurable permissions add flexibility but complexity
  - Decision Status: PENDING

- DEC-2: Authentication Method
  - Claude Position: Session-based authentication with Spring Security
  - Codex Position: JWT-based stateless authentication
  - Tradeoff Summary: Session is simpler for embedded frontend; JWT better for scalability and mobile APIs
  - Decision Status: PENDING

- DEC-3: Task Board Complexity
  - Claude Position: Simple 3-column Kanban (To Do, In Progress, Done)
  - Codex Position: Full Kanban with swimlanes, drag-drop, dependencies
  - Tradeoff Summary: Simple board meets requirements; advanced features add significant development time
  - Decision Status: PENDING

## Implementation Notes

### Code Style Requirements
- Implementation code and comments must NOT contain plan-specific terminology such as "AC-", "Milestone", "Step", "Phase", or similar workflow markers
- These terms are for plan documentation only, not for the resulting codebase
- Use descriptive, domain-appropriate naming in code instead

--- Original Design Draft Start ---

# Requirement

现在我想写一个java基于springboot的后端应用，需要有swagger文档；带有前端页面，启动后都从统一端口暴露服务；项目的主要需求是：1.用于企业员工管理 2.支持员工邮箱注册账号，邮箱登录，邮箱服务于留在配置文件配置后运行代码就可以发送验证码 3.用户分角色，如 ceo，cto，程序员等有组织结构；管理员可以剔除用户 4.任务看板，高一级的用户可以给下级安排任务，并且有看板，自己的任务，自己给别人分配的任务 4.数据库使用mysql，前端使用react

可以配置的结构，管理员是系统角色，看板按照传统的进行编写就行，前端内嵌到spring

---

## Standard Deliverables (mandatory for every project)

- **README.md** — must be included at the project root with: project title & description, prerequisites, installation steps, usage examples with code snippets, configuration options, and project structure overview.
- **Git commits** — use conventional commit prefix `feat:` for all commits.

--- Original Design Draft End ---

import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import styles from './Navbar.module.css';

/**
 * Top navigation bar shown on all authenticated pages.
 */
function Navbar({ currentUser }) {
  const navigate = useNavigate();

  const handleLogout = async () => {
    await authService.logout();
    navigate('/login');
  };

  return (
    <nav className={styles.navbar}>
      <div className={styles.brand}>Employee Manager</div>
      <ul className={styles.links}>
        <li>
          <NavLink to="/dashboard" className={({ isActive }) => isActive ? styles.active : ''}>
            Dashboard
          </NavLink>
        </li>
        <li>
          <NavLink to="/kanban" className={({ isActive }) => isActive ? styles.active : ''}>
            Kanban Board
          </NavLink>
        </li>
        {currentUser?.role === 'ADMIN' && (
          <li>
            <NavLink to="/admin" className={({ isActive }) => isActive ? styles.active : ''}>
              Admin
            </NavLink>
          </li>
        )}
      </ul>
      <div className={styles.userSection}>
        {currentUser && (
          <span className={styles.username}>{currentUser.fullName}</span>
        )}
        <button onClick={handleLogout} className={styles.logoutButton}>
          Logout
        </button>
      </div>
    </nav>
  );
}

export default Navbar;

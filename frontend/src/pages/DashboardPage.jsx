import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar.jsx';
import { authService } from '../services/authService';
import styles from './DashboardPage.module.css';

function DashboardPage() {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    authService.getCurrentUser()
      .then((res) => setCurrentUser(res.data))
      .catch(() => {});
  }, []);

  return (
    <>
      <Navbar currentUser={currentUser} />
      <main className={styles.main}>
        <h1 className={styles.heading}>
          Welcome{currentUser ? `, ${currentUser.fullName}` : ''}
        </h1>
        {currentUser && (
          <div className={styles.profileCard}>
            <p><span className={styles.label}>Email:</span> {currentUser.email}</p>
            <p><span className={styles.label}>Role:</span> {currentUser.role}</p>
          </div>
        )}
        <p className={styles.hint}>
          Use the <strong>Kanban Board</strong> to view and manage your tasks.
        </p>
      </main>
    </>
  );
}

export default DashboardPage;

import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar.jsx';
import { authService } from '../services/authService';
import { userService } from '../services/userService';
import styles from './AdminPage.module.css';

function AdminPage() {
  const [currentUser, setCurrentUser] = useState(null);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    authService.getCurrentUser().then((res) => setCurrentUser(res.data));
    userService.getAllUsers().then((res) => {
      setUsers(res.data);
      setLoading(false);
    });
  }, []);

  const handleDeactivate = async (userId) => {
    if (!window.confirm('Deactivate this user?')) return;
    await userService.deactivateUser(userId);
    setUsers((prev) => prev.filter((u) => u.id !== userId));
  };

  return (
    <>
      <Navbar currentUser={currentUser} />
      <main className={styles.main}>
        <h1 className={styles.heading}>User Administration</h1>
        {loading ? (
          <p className={styles.loading}>Loading users…</p>
        ) : (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.fullName}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td>{user.active ? 'Active' : 'Inactive'}</td>
                  <td>
                    {user.active && (
                      <button
                        className={styles.deactivateBtn}
                        onClick={() => handleDeactivate(user.id)}
                      >
                        Deactivate
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </main>
    </>
  );
}

export default AdminPage;

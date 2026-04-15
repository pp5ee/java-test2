import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar.jsx';
import { authService } from '../services/authService';
import { taskService } from '../services/taskService';
import styles from './KanbanPage.module.css';

const COLUMNS = [
  { key: 'TODO', label: 'To Do' },
  { key: 'IN_PROGRESS', label: 'In Progress' },
  { key: 'DONE', label: 'Done' },
];

function KanbanPage() {
  const [currentUser, setCurrentUser] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    authService.getCurrentUser().then((res) => setCurrentUser(res.data));
    taskService.getMyTasks().then((res) => {
      setTasks(res.data);
      setLoading(false);
    });
  }, []);

  const tasksByStatus = (status) => tasks.filter((t) => t.status === status);

  return (
    <>
      <Navbar currentUser={currentUser} />
      <main className={styles.main}>
        <h1 className={styles.heading}>Kanban Board</h1>
        {loading ? (
          <p className={styles.loading}>Loading tasks…</p>
        ) : (
          <div className={styles.board}>
            {COLUMNS.map((col) => (
              <div key={col.key} className={styles.column}>
                <h2 className={styles.columnTitle}>{col.label}</h2>
                <div className={styles.cards}>
                  {tasksByStatus(col.key).map((task) => (
                    <div key={task.id} className={styles.card}>
                      <p className={styles.cardTitle}>{task.title}</p>
                      {task.description && (
                        <p className={styles.cardDesc}>{task.description}</p>
                      )}
                      <p className={styles.cardMeta}>
                        Assigned to: <strong>{task.assigneeName || 'Me'}</strong>
                      </p>
                    </div>
                  ))}
                  {tasksByStatus(col.key).length === 0 && (
                    <p className={styles.empty}>No tasks here</p>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </>
  );
}

export default KanbanPage;

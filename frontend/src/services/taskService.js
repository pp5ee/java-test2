import apiClient from './apiClient';

export const taskService = {
  /**
   * Fetch all tasks visible to the current user (assigned to or created by them).
   */
  getMyTasks() {
    return apiClient.get('/tasks/mine');
  },

  /**
   * Fetch tasks created by the current user (assigned to subordinates).
   */
  getAssignedByMe() {
    return apiClient.get('/tasks/assigned-by-me');
  },

  /**
   * Create a new task and optionally assign it to a subordinate.
   * @param {Object} payload - { title, description, assigneeId, dueDate }
   */
  createTask(payload) {
    return apiClient.post('/tasks', payload);
  },

  /**
   * Update task details.
   * @param {number} taskId
   * @param {Object} payload - partial task fields
   */
  updateTask(taskId, payload) {
    return apiClient.put(`/tasks/${taskId}`, payload);
  },

  /**
   * Move a task to a new status column on the Kanban board.
   * @param {number} taskId
   * @param {string} status - 'TODO' | 'IN_PROGRESS' | 'DONE'
   */
  moveTask(taskId, status) {
    return apiClient.patch(`/tasks/${taskId}/status`, { status });
  },

  /**
   * Delete a task by ID.
   * @param {number} taskId
   */
  deleteTask(taskId) {
    return apiClient.delete(`/tasks/${taskId}`);
  },
};

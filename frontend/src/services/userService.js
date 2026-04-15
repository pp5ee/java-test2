import apiClient from './apiClient';

export const userService = {
  /**
   * Fetch all users (admin only).
   */
  getAllUsers() {
    return apiClient.get('/users');
  },

  /**
   * Fetch users that are direct reports of the current user.
   */
  getSubordinates() {
    return apiClient.get('/users/subordinates');
  },

  /**
   * Fetch a single user by ID.
   * @param {number} userId
   */
  getUserById(userId) {
    return apiClient.get(`/users/${userId}`);
  },

  /**
   * Create a new user with a specific role (admin only).
   * @param {Object} payload - { email, fullName, role, managerId }
   */
  createUser(payload) {
    return apiClient.post('/users', payload);
  },

  /**
   * Deactivate (soft-delete) a user account.
   * @param {number} userId
   */
  deactivateUser(userId) {
    return apiClient.delete(`/users/${userId}`);
  },

  /**
   * Fetch available roles for the organizational hierarchy.
   */
  getAvailableRoles() {
    return apiClient.get('/users/roles');
  },
};

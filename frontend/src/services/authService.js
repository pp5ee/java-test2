import apiClient from './apiClient';

export const authService = {
  /**
   * Register a new employee account.
   * @param {Object} payload - { email, password, fullName }
   */
  register(payload) {
    return apiClient.post('/auth/register', payload);
  },

  /**
   * Verify the email address with a code sent to the user.
   * @param {Object} payload - { email, code }
   */
  verifyEmail(payload) {
    return apiClient.post('/auth/verify-email', payload);
  },

  /**
   * Resend the email verification code.
   * @param {string} email
   */
  resendVerificationCode(email) {
    return apiClient.post('/auth/resend-code', { email });
  },

  /**
   * Log in with email and password; returns a JWT token.
   * @param {Object} payload - { email, password }
   */
  login(payload) {
    return apiClient.post('/auth/login', payload).then((res) => {
      const { token } = res.data;
      if (token) {
        localStorage.setItem('auth_token', token);
      }
      return res;
    });
  },

  /**
   * Log out the current user and clear the stored token.
   */
  logout() {
    return apiClient.post('/auth/logout').finally(() => {
      localStorage.removeItem('auth_token');
    });
  },

  /**
   * Fetch the profile of the currently authenticated user.
   */
  getCurrentUser() {
    return apiClient.get('/auth/me');
  },

  /**
   * Returns true if a JWT token is present in local storage.
   */
  isAuthenticated() {
    return Boolean(localStorage.getItem('auth_token'));
  },
};

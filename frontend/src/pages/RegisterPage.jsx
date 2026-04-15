import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../services/authService';
import styles from './AuthPages.module.css';

const STEPS = { REGISTER: 'register', VERIFY: 'verify' };

function RegisterPage() {
  const navigate = useNavigate();
  const [step, setStep] = useState(STEPS.REGISTER);
  const [form, setForm] = useState({ email: '', fullName: '', password: '' });
  const [verifyCode, setVerifyCode] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await authService.register(form);
      setStep(STEPS.VERIFY);
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleVerify = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await authService.verifyEmail({ email: form.email, code: verifyCode });
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Verification failed. Please check your code.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.card}>
        <h1 className={styles.title}>Employee Management</h1>

        {step === STEPS.REGISTER && (
          <>
            <h2 className={styles.subtitle}>Create Account</h2>
            {error && <p className={styles.error}>{error}</p>}
            <form onSubmit={handleRegister} className={styles.form}>
              <label className={styles.label}>Full Name</label>
              <input
                className={styles.input}
                type="text"
                name="fullName"
                value={form.fullName}
                onChange={handleChange}
                placeholder="Jane Smith"
                required
              />

              <label className={styles.label}>Email</label>
              <input
                className={styles.input}
                type="email"
                name="email"
                value={form.email}
                onChange={handleChange}
                placeholder="you@company.com"
                required
              />

              <label className={styles.label}>Password</label>
              <input
                className={styles.input}
                type="password"
                name="password"
                value={form.password}
                onChange={handleChange}
                placeholder="At least 8 characters"
                minLength={8}
                required
              />

              <button className={styles.button} type="submit" disabled={loading}>
                {loading ? 'Registering…' : 'Register'}
              </button>
            </form>
            <p className={styles.footer}>
              Already have an account?{' '}
              <Link to="/login" className={styles.link}>Sign In</Link>
            </p>
          </>
        )}

        {step === STEPS.VERIFY && (
          <>
            <h2 className={styles.subtitle}>Verify Your Email</h2>
            <p className={styles.info}>
              A verification code has been sent to <strong>{form.email}</strong>.
            </p>
            {error && <p className={styles.error}>{error}</p>}
            <form onSubmit={handleVerify} className={styles.form}>
              <label className={styles.label}>Verification Code</label>
              <input
                className={styles.input}
                type="text"
                value={verifyCode}
                onChange={(e) => setVerifyCode(e.target.value)}
                placeholder="6-digit code"
                required
              />
              <button className={styles.button} type="submit" disabled={loading}>
                {loading ? 'Verifying…' : 'Verify'}
              </button>
            </form>
            <p className={styles.footer}>
              Didn&apos;t receive it?{' '}
              <button
                className={styles.link}
                style={{ background: 'none', border: 'none', cursor: 'pointer', padding: 0 }}
                onClick={() => authService.resendVerificationCode(form.email)}
              >
                Resend code
              </button>
            </p>
          </>
        )}
      </div>
    </div>
  );
}

export default RegisterPage;

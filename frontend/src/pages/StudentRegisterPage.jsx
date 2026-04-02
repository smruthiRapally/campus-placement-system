import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function StudentRegisterPage() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ fullName: '', email: '', password: '', department: '', phone: '', cgpa: '', graduationYear: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const set = f => e => setForm({ ...form, [f]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault(); setLoading(true); setError('');
    try {
      await register({ ...form, role: 'STUDENT', cgpa: form.cgpa ? Number(form.cgpa) : null, graduationYear: form.graduationYear ? Number(form.graduationYear) : null });
      navigate('/student/dashboard');
    } catch (err) {
      const msg = err.response?.data?.message || '';
      if (msg.toLowerCase().includes('already') || msg.toLowerCase().includes('exists')) {
        setError('An account with this email already exists. Please sign in instead.');
      } else {
        setError(msg || 'Registration failed. Please try again.');
      }
    } finally { setLoading(false); }
  };

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <div className="auth-logo">
          <div className="logo-circle">👨‍🎓</div>
          <h2>Student Registration</h2>
          <p className="auth-desc">Create your placement portal account</p>
        </div>
        {error && (
          <div className="alert alert-error">
            ⚠ {error}
            {error.includes('already exists') && (
              <span> <Link to="/student/login" style={{ color: '#991b1b', fontWeight: 600 }}>Sign in here</Link></span>
            )}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Full Name</label>
            <input placeholder="e.g. Rapally Smruthi" required onChange={set('fullName')} />
          </div>
          <div className="field">
            <label>College Email</label>
            <input type="email" placeholder="23eg104c23@anurag.edu.in" required onChange={set('email')} />
          </div>
          <div className="field">
            <label>Password</label>
            <input type="password" placeholder="Minimum 6 characters" required onChange={set('password')} />
          </div>
          <div className="form-row">
            <div className="field">
              <label>Department</label>
              <input placeholder="e.g. ECE, CSE" onChange={set('department')} />
            </div>
            <div className="field">
              <label>Phone</label>
              <input placeholder="10-digit number" onChange={set('phone')} />
            </div>
          </div>
          <div className="form-row">
            <div className="field">
              <label>CGPA</label>
              <input type="number" step="0.01" min="0" max="10" placeholder="e.g. 8.5" onChange={set('cgpa')} />
            </div>
            <div className="field">
              <label>Graduation Year</label>
              <select onChange={set('graduationYear')} defaultValue="">
                <option value="" disabled>Select Year</option>
                {Array.from({ length: 11 }, (_, i) => 2025 + i).map(y => <option key={y} value={y}>{y}</option>)}
              </select>
            </div>
          </div>
          <button className="btn btn-primary btn-full btn-lg" type="submit" disabled={loading}>
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
        </form>
        <p className="auth-link">Already have an account? <Link to="/student/login">Sign in</Link></p>
        <p className="auth-link" style={{ marginTop: 8 }}><Link to="/">← Back to home</Link></p>
      </div>
    </div>
  );
}

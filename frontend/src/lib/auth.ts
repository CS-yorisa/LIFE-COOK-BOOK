import { writable } from 'svelte/store';

const baseUrl = 'http://localhost:8080';
export interface User {
	email: string;
	password: string;
}

export interface AuthState {
	user: User | null;
	isAuthenticated: boolean;
	isLoading: boolean;
}

function createAuthStore() {
	const { subscribe, set, update } = writable<AuthState>({
		user: null,
		isAuthenticated: false,
		isLoading: true
	});

	return {
		subscribe,
		login: async (user: User): Promise<boolean> => {
			update(state => ({ ...state, isLoading: true }));
			try {
				const res = await fetch(`${baseUrl}/member/signin`, {
					method: 'POST',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify({ email: user.email, passWord: user.password })
				});
				if (!res.ok) {
					const errBody = await res.json().catch(() => null);
					throw new Error(errBody?.message || 'Login failed');
				}
				const data = (await res.json()) as { access: string; refresh: string };
				if (typeof window !== 'undefined') {
					localStorage.setItem('access', data.access);
					localStorage.setItem('refresh', data.refresh);
				}
				update(state => ({
					...state,
					isAuthenticated: true,
					isLoading: false
				}));
				return true;
			} catch (error) {
				update(state => ({ ...state, isLoading: false }));
				throw error;
			}
		},
		logout: () => {
			update(state => ({
				...state,
				user: null,
				isAuthenticated: false,
				isLoading: false
			}));
			// 로컬 스토리지에서 제거
			localStorage.removeItem('access');
			localStorage.removeItem('refresh');
		},
		initialize: () => {
			// 페이지 로드 시 로컬 스토리지에서 사용자 정보 복원
			const savedUser = localStorage.getItem('user');
			if (savedUser) {
				try {
					const user = JSON.parse(savedUser);
					update(state => ({
						...state,
						user,
						isAuthenticated: true,
						isLoading: false
					}));
				} catch (error) {
					console.error('Failed to parse saved user:', error);
					localStorage.removeItem('user');
					update(state => ({
						...state,
						isLoading: false
					}));
				}
			} else {
				update(state => ({
					...state,
					isLoading: false
				}));
			}
		}
	};
}

export const auth = createAuthStore();

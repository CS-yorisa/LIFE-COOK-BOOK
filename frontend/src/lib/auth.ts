import { writable } from 'svelte/store';

export interface User {
	id: string;
	email: string;
	name: string;
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
		login: (user: User) => {
			update(state => ({
				...state,
				user,
				isAuthenticated: true,
				isLoading: false
			}));
			// 로컬 스토리지에 저장
			localStorage.setItem('user', JSON.stringify(user));
		},
		logout: () => {
			update(state => ({
				...state,
				user: null,
				isAuthenticated: false,
				isLoading: false
			}));
			// 로컬 스토리지에서 제거
			localStorage.removeItem('user');
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

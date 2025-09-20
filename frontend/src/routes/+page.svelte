<script>
	import { goto } from '$app/navigation';
	import Logo from '$lib/components/common/Logo.svelte';
</script>

<div class="bg-muted-background flex min-h-screen items-center justify-center">
	<div class="mx-auto max-w-4xl px-6">
		<div class="mb-12">
			<div class="mb-10 flex justify-center">
				<Logo size="large" />
			</div>
			<div class="text-center">
				<h1 class="mb-6 text-3xl font-bold text-gray-900">
					가계부를 기록하고 자산을 효율적으로 관리하세요
				</h1>

				<p class="mx-auto mb-8 max-w-2xl text-xl text-gray-600">
					일상의 모든 지출을 기록하고, 자산을 체계적으로 관리하며, 지인들과 함께 목표를 공유하는
					가계부 앱입니다.
				</p>
			</div>
		</div>

		<div class="flex flex-col justify-center gap-4 sm:flex-row">
			<button
				onclick={() => goto('/main')}
				class="bg-muted hover:bg-muted/80 rounded-lg px-8 py-3 text-lg font-medium text-white transition-colors"
			>
				시작하기
			</button>
		</div>

		<div class="mt-12 grid grid-cols-1 gap-8 md:grid-cols-3">
			<div class="rounded-lg bg-white p-6 shadow-lg">
				<div class="mb-4 text-3xl">📊</div>
				<h3 class="mb-2 text-xl font-semibold">가계부 기록</h3>
				<p class="text-gray-600">일상의 모든 수입과 지출을 체계적으로 기록하고 관리하세요</p>
			</div>

			<div class="rounded-lg bg-white p-6 shadow-lg">
				<div class="mb-4 text-3xl">💰</div>
				<h3 class="mb-2 text-xl font-semibold">자산 관리</h3>
				<p class="text-gray-600">다양한 자산을 통합하여 효율적으로 관리하세요</p>
			</div>

			<div class="rounded-lg bg-white p-6 shadow-lg">
				<div class="mb-4 text-3xl">👥</div>
				<h3 class="mb-2 text-xl font-semibold">그룹 공유</h3>
				<p class="text-gray-600">지인들과 함께 가계부를 공유하고 목표를 달성하세요.</p>
			</div>
		</div>
	</div>
</div>

<script lang="ts">
	import { goto } from '$app/navigation';
	import { Tabs, TabsList, TabsTrigger } from '$lib/components/ui/tabs';
	import Logo from '$lib/components/common/Logo.svelte';

	// Tab configuration
	const tabs = [
		{ value: 'main', label: '대시보드', path: '/main' },
		{ value: 'gageboo', label: '가계부', path: '/gageboo' },
		{ value: 'asset', label: '자산', path: '/asset' },
		{ value: 'group', label: '그룹', path: '/group' }
	];

	// Props
	let { activeTab = 'main', children } = $props();

	function handleTabChange(value: string) {
		activeTab = value;
		const tab = tabs.find(t => t.value === value);
		if (tab) {
			goto(tab.path);
		}
	}
</script>

<div class="bg-muted-background min-h-screen min-w-[1600px]">
	<div class="flex justify-center pt-[80px]">
		<div class="relative w-[1200px]">
			<!-- Logo positioned at top-left of content area -->
			<div class="absolute -top-9 -left-40 z-10">
				<Logo size="default" />
			</div>

			<!-- shadcn Tabs 영역 -->
			<div class="mb-4 flex justify-center">
				<Tabs bind:value={activeTab} onValueChange={handleTabChange} class="w-fit">
					<TabsList class="h-12 w-fit rounded-lg bg-sky-100 shadow-sm dark:bg-sky-900/20">
						{#each tabs as tab}
							<TabsTrigger
								value={tab.value}
								class="w-28 text-lg font-medium text-sky-600 hover:bg-sky-50 hover:text-sky-700 data-[state=active]:bg-white data-[state=active]:text-sky-700 data-[state=active]:shadow-sm dark:text-sky-400 dark:hover:bg-sky-800/50 dark:data-[state=active]:bg-sky-800 dark:data-[state=active]:text-sky-300"
							>
								{tab.label}
							</TabsTrigger>
						{/each}
					</TabsList>
				</Tabs>
			</div>

			<!-- 흰색 컨텐츠 영역 -->
			<div class="min-h-screen rounded-lg bg-white shadow-lg">
				<div class="px-24 py-14">
					{@render children()}
				</div>
			</div>
		</div>
	</div>
</div>

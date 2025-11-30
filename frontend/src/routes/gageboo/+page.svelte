<script lang="ts">
	import MainLayout from '$lib/components/common/MainLayout.svelte';
	import ExpenseProgressBar from './ExpenseProgressBar.svelte';
	import IncomeProgressBar from './IncomeProgressBar.svelte';
	import Modal from './Modal.svelte';

	let showModal = false;

	function handleClick() {
		showModal = true;
	}

	function closeModal() {
		showModal = false;
	}

	let expenditure = 485000;
	let income = 2335000;
	let budgetUsage = 50;
	let savingsRate = 23;

	let transactions = [
		{
			date: '2025-08-01 12:00:00',
			category: '식비',
			description: '샌드위치 구매 (XX마트)',
			amount: -20530
		},
		{
			date: '2025-08-01 12:00:00',
			category: '식비',
			description: '샌드위치 구매 (XX마트)',
			amount: -20530
		},
		{
			date: '2025-08-01 12:00:00',
			category: '식비',
			description: '샌드위치 구매 (XX마트)',
			amount: -20530
		},
		{
			date: '2025-08-01 12:00:00',
			category: '식비',
			description: '샌드위치 구매 (XX마트)',
			amount: -20530
		}
	];

	export let onClick = () => {
		handleClick();
	};

	let isHovered = false;
</script>

<style>
	.summary {
		display: flex;
		justify-content: space-between;
		margin: 2rem 0 5rem 0;
		gap: 1rem;
	}

	.box {
		flex: 1;
		padding-right: 2rem;
	}

	.box h3 {
		margin-bottom: 0.5rem;
	}

	table {
		width: 100%;
		border-collapse: collapse;
		margin-top: 2rem;
	}

	th, td {
		border: 1px solid #ddd;
		padding: 0.75rem;
		text-align: left;
	}

	th {
		background-color: #F4FCF4;
	}

	h3 {
		font-size: 2rem;
	}

	.amount {
		text-align: right;
		color: red;
	}

	.percent {
		font-size: 1.2rem;
		margin-bottom: 0.5rem;
	}

	 .fab-container {
		 position: fixed;
		 bottom: 24px;
		 right: 24px;
		 z-index: 1000;
	 }

	.fab-container {
		position: fixed;
		bottom: 24px;
		right: 24px;
		z-index: 1000;
	}

	.tooltip {
		position: absolute;
		right: 70px;
		bottom: 16px;
		background-color: #333;
		color: white;
		padding: 6px 12px;
		border-radius: 6px;
		font-size: 0.9rem;
		white-space: nowrap;
		opacity: 0;
		transform: translateY(5px);
		transition: opacity 0.3s ease, transform 0.3s ease;
		pointer-events: none;
	}

	.tooltip.show {
		opacity: 1;
		transform: translateY(0);
	}

	.fab {
		width: 56px;
		height: 56px;
		border-radius: 50%;
		background-color: #88FF88;
		color: white;
		font-size: 32px;
		display: flex;
		justify-content: center;
		align-items: center;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
		cursor: pointer;
		transition: background-color 0.3s ease;
		border: none;
	}

	.fab:hover {
		background-color: #32AC32;
	}

</style>

<MainLayout activeTab="gageboo">

	<div class="summary">
		<div class="box">
			<h3 class="font-bold">지출</h3>
			<p style="font-size: 2rem; color: #339AF0; padding-bottom: 0.5rem;" class="font-bold">{expenditure.toLocaleString()} 원</p>
			<p class="percent">목표 예산의 {budgetUsage}%를 사용했어요!</p>
			<ExpenseProgressBar percent={budgetUsage} />
		</div>
		<div class="box">
			<h3 class="font-bold">수입</h3>
			<p style="font-size: 2rem; color: #FF4890; padding-bottom: 0.5rem" class="font-bold">{income.toLocaleString()} 원</p>
			<p class="percent">수입의 {savingsRate}%를 저축했어요!</p>
			<IncomeProgressBar percent={savingsRate} />
		</div>
	</div>

	<h3 class="font-bold">전체 내역</h3>
	<table>
		<thead>
		<tr>
			<th>일시</th>
			<th>분류</th>
			<th>내용</th>
			<th>금액</th>
		</tr>
		</thead>
		<tbody>
		{#each transactions as tx}
			<tr>
				<td>{tx.date}</td>
				<td>{tx.category}</td>
				<td>{tx.description}</td>
				<td class="amount">{tx.amount.toLocaleString()} 원</td>
			</tr>
		{/each}
		</tbody>
	</table>

	<div class="fab-container">
		<div class="tooltip {isHovered ? 'show' : ''}">가계부 작성하기</div>
		<button
				class="fab"
				aria-label="가계부 작성하기"
				on:click={onClick}
				on:mouseenter={() => isHovered = true}
				on:mouseleave={() => isHovered = false}
		>
			+
		</button>

		<Modal visible={showModal} onClose={closeModal} />
	</div>
</MainLayout>

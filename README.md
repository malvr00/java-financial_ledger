# Programming Practice with Java Access

용돈기입장 DB(access) 

## main Class [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedgerMain.java)
   <img src="https://user-images.githubusercontent.com/77275513/131303653-701ee612-133f-469a-bfce-8b6b0a68b5b5.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
1. '▲', '▼' 클릭하여 날짜 조정
2. 카테고리와 날짜를 선택하여 기록 저장<br/>

   <img src="https://user-images.githubusercontent.com/77275513/131304453-1e9652c3-73a9-4d7c-9b94-cc758c94ccc2.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
1. 마우스 오른쪽 클릭시 선택한 요일에 등록한 기록 보여줌 [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L163-L204) [[요일 Select]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L213-L235)
<br/>
## Calendar Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/CalendarClass.java)

## Frame Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java)
* 지출금액 & 사용내역 Insert [코드](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L279-L300)
* 일일 지출금액 & 사용내역 보기 [코드](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L127-L167)
* 년 & 월 총 계산 [코드](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L238-L274)
* event [코드](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L233-L321)
## DB Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_DAO.java)

## Dlg Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/DlgDetail.java)
   <img src="https://user-images.githubusercontent.com/77275513/131304812-729cbca2-7806-4891-9a1f-aa4c9e8937eb.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
1. 카테고리 선택과 년, 월 입력 시 카테고리 별 총 금액 출력 [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/DlgDetail.java#L112-L156)

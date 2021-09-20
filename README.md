# Programming Practice with Java Access

용돈기입장 DB(access) 

## main Class [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedgerMain.java)
1. '▲', '▼' 클릭하여 날짜 조정  [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L291-L338)
2. 달력을 왼쪽 마우스로 클릭하여 날짜 지정
3. 카테고리와 날짜를 선택하여 기록 & 저장<br/>
   <img src="https://user-images.githubusercontent.com/77275513/131303653-701ee612-133f-469a-bfce-8b6b0a68b5b5.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
   
1. 달력에 보고싶은 날을 마우스 오른쪽 클릭시 선택한 요일에 등록한 기록 보여줌 [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L163-L204) [[요일별 등록 개수 카운트]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L213-L235)
<br/>
   <img src="https://user-images.githubusercontent.com/77275513/131304453-1e9652c3-73a9-4d7c-9b94-cc758c94ccc2.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
   
---

## Calendar Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/CalendarClass.java)
* 달력 출력 & 관리 Class
---

## Frame Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java)
* 년 & 월 총 계산 [코드](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L238-L274)
* event [코드](https://github.com/malvr00/java-financial_ledger/blob/e53313572634c188b96f03c0ae385dae35fd1106/FinancialLedger/src/financialLedger/FinancialLedger_Frame.java#L290-L391)

---

## DB Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/FinancialLedger_DAO.java)
* try Catch 사용 빈도 줄이기 위해 개별로 작성

---

## Dlg Class[[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/DlgDetail.java)
   <img src="https://user-images.githubusercontent.com/77275513/131304812-729cbca2-7806-4891-9a1f-aa4c9e8937eb.PNG" width="600px" height="300px" title="100px" alt="RubberDuck"></img><br/><br/>
1. 카테고리 선택과 년, 월 입력 시 카테고리 별 총 금액 출력 [[코드]](https://github.com/malvr00/java-financial_ledger/blob/main/FinancialLedger/src/financialLedger/DlgDetail.java#L112-L156)

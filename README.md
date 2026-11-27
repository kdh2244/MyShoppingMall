# MyShoppingMall

# 사용 기술
- spring-boot , mysql , kakao login api , jwt , kako pay api

# 개발 환경
-window10 , spring boot 4.1.1 

# 전체 아키텍쳐
<img width="1705" height="894" alt="프로그램 아키텍쳐" src="https://github.com/user-attachments/assets/d11d1b10-e0c3-4cf8-8dd8-bc0abd4c608c" />


# ERD 
<img width="962" height="342" alt="제목 없는 다이어그램 drawio" src="https://github.com/user-attachments/assets/75c1804f-1a01-4c38-8a60-b8bb69c983b1" />

# rest api
<img width="479" height="527" alt="스크린샷(1441)" src="https://github.com/user-attachments/assets/e73faa8f-e521-4ee5-a228-710ef99a29e6" /> <br>
<img width="423" height="156" alt="스크린샷(1442)" src="https://github.com/user-attachments/assets/47f6ddfd-da88-467d-b05b-bafe542798f4" /> <br>
<img width="475" height="259" alt="스크린샷(1443)" src="https://github.com/user-attachments/assets/9a1a2311-749b-47b8-bc5d-4dc7b493e1f0" /> <br>
<img width="419" height="243" alt="스크린샷(1444)" src="https://github.com/user-attachments/assets/d0ea9c7d-28bb-4e67-8f22-c978336c3501" /> <br>
<img width="542" height="314" alt="스크린샷(1445)" src="https://github.com/user-attachments/assets/4c056f65-6664-41b4-abc1-aef1caf88e48" /> <br>
<img width="514" height="222" alt="스크린샷(1446)" src="https://github.com/user-attachments/assets/3f2a63ac-566b-4611-91af-3bc019f7be7a" /><br>
<img width="476" height="416" alt="스크린샷(1447)" src="https://github.com/user-attachments/assets/e453e99f-e5da-4f20-998e-0a583553b400" /><br>
<img width="418" height="498" alt="스크린샷(1448)" src="https://github.com/user-attachments/assets/28b29e66-e170-4ce7-b1fe-67be3c5af6eb" /><br>
<img width="547" height="218" alt="스크린샷(1449)" src="https://github.com/user-attachments/assets/604d109f-d56f-4fd2-ad71-513592f78989" /><br>
<img width="732" height="364" alt="스크린샷(1450)" src="https://github.com/user-attachments/assets/061b470b-c016-42ed-bb66-a342af96006e" /><br>
<img width="412" height="203" alt="스크린샷(1452)" src="https://github.com/user-attachments/assets/475ae152-bed7-4eb7-bf2a-06c22ad7839b" /> <br>
<img width="586" height="226" alt="스크린샷(1453)" src="https://github.com/user-attachments/assets/0f366863-47aa-4d39-88f9-99d20fcb57cb" />

# 목적 
spring boot 와 kakao login api , jwt ,kakao pay 결제 api 를 사용해서 쇼핑몰 백엔드 기획부터 배포까지 구현하는 프로젝트.

# 주요 기능
1.로그인 : 카카오톡 계정으로 로그인 <br>
2.product : 키워드로 상품 조회, 각 상품 상세 조회
3.cart : 장바구니에 상품 담기 기능, 장바구니 삭제 , 장바구니 조회.
4.order : 주문 기능 , kakao pay를 통해 결제 가능.

# 트러블 슈팅

3. cart 가져오는 코드에서 문제가 발생 cart 의 List<cartitem> 이 있는데 문제는 cartitem도 cart를 가지고 있는

   양방향 관계라서 데이터를 가져오면

```
public class CartController {

    final private CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<?> getCart(){

        //아래 이메일 가져오는거 따로 빼는 게 좋을듯
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Cart cart =  cartService.getCart(email);

        ResponseCartDTO responseCartDTO = ResponseCartDTO.toResponseCartDTO(cart);
        return ResponseEntity.ok(responseCartDTO);

    }
```



{

  "id": 1,

  "totalPrice": 1000000,

  "customer": {

​    "id": 2,

​    "email": "kdoh3324@naver.com",

​    "name": "김도훈",

​    "createdAt": "2026-10-22",

​    "role": "USER",

​    "password": "jlesjfljsef",

​    "orderList": []

  },

  "cartItemList": [

​    {

​      "id": 1,

​      "totalPrice": 100000,

​      "totalQuantity": 2,

​      "cart": {

​        "id": 1,

​        "totalPrice": 1000000,

​        "customer": {

​          "id": 2,

​          "email": "kdoh3324@naver.com",

​          "name": "김도훈",

​          "createdAt": "2026-10-22",

​          "role": "USER",

​          "password": "jlesjfljsef",

​          "orderList": []

​        },

​        "cartItemList": [

​          {

​            "id": 1,

​            "totalPrice": 100000,

​            "totalQuantity": 2,

​            "cart": {

​              "id": 1,

​              "totalPrice": 1000000,

​              "customer": {

​                "id": 2,

​                "email": "kdoh3324@naver.com",

​                "name": "김도훈",

​                "createdAt": "2026-10-22",

​                "role": "USER",

​                "password": "jlesjfljsef",

​                "orderList": []

​              }, 이런식으로 무한히 순환 되는 문제가 발생.

해결법

- ResponseCartDTO를 만들어서 필요한 데이터만 추가하고 순환참조가 일어나지 않도록 

  List<CartItem> 에서 필요한 cartItem 멤버만 따로 가져온다.

  ```
  @Getter
  @Setter
  @RequiredArgsConstructor
  public class ResponseCartDTO {
  
      private List<ResponseCartItem> responseCartItems;
  
      @Getter
      @AllArgsConstructor
      class ResponseCartItem{
          private int totalPrice;
          private int totalQuantity;
          private String productName;
      }
  
      public static ResponseCartDTO toResponseCartDTO(Cart cart){
  
  //        ObjectMapper objectMapper = new ObjectMapper();
  //        String s = objectMapper.writeValueAsString(cart.getCartItemList());
  //
  //        System.out.println("cart : "+ s);
          ResponseCartDTO responseCartDTO = new ResponseCartDTO();
          List<ResponseCartItem> responseCartItemList;
          responseCartItemList = cart.getCartItemList().stream().map((cartItem) -> {
  
              int totalPrice =cartItem.getTotalPrice();
              int totalQuantity = cartItem.getTotalQuantity();
              String productName = cartItem.getProduct().getName();
  
              return responseCartDTO.new ResponseCartItem(totalPrice,totalQuantity,productName);
          })
                  .collect(Collectors.toList());
  
          responseCartDTO.setResponseCartItems(responseCartItemList);
  
          ObjectMapper objectMapper = new ObjectMapper();
          String s = objectMapper.writeValueAsString(responseCartItemList);
          System.out.println("responseCartItemList " + s);
  
          return responseCartDTO;
      }
  
  }
  ```

toResponseCartDTO를 두어 cart를 입력 받아서 cartitemlist로 필요한 값만 가져온다.

4. kakaopay api를 이용해서 결제 처리를 구현하는데 kakaopay api에서 결제 준비 요청을 kakao  에 보내면 

   결제 번호인 tid를 받는데 이게 실제 결제 허용 요청을 할 때 필요해서 서버에서 저장을 해야하는데 어떻게 저장해야 나중에 다시 가져와서 결제 허용 요청을 보낼지 문제.

   - db에 order에 tid속성을 추가하려고 했는데 이러면 서버에서 tid를 어떻게 가져올지 의문이 생김

   - 그래서 따로 테이블을 만들었는데 이래도 어떻게 가져올지 의문임

   - 그래서 customer 테이블에 tid 속성을 추가할까 생각하는데 이러면 tid의 값이 여러개가 생기면 문제가 됨.어떤걸 가져와야 할지 모르니까. 근데 중복이 생길 가능성이 없는 건 아니라서 중복을 차단할 수 있다면 가능함.

     그니까 결제를 굳이 한번에 두번씩 동시에 하는 기능이 있을 필요는 없으니까 , 

     막아도 될 듯. 그럼 어떻게 막을까? 주문 상태가 PENDING,IN_PROGRESS가 있는 customer라면

     /order 막으면 order랑 orderitem생기지도 않고 kakaopay api 호출도 못하니까 tid가 생기지 않으니까 괜찮을 듯. 

​	해결 cusomer에 tid 추가 하고 

중복해서 주문하면 tid가 여러개 생기기 때문에 막기 위해서 추가

```
@PostMapping
public ResponseEntity<?> order(@RequestBody RequestOrderDTO requestOrderDTO){
    System.out.println("order 로 들어옴");
    //현재 주문 준비 중이거나 준비 진행중인 고객이라면 기다리라고 함.
    List<Orders> orderList = authUtil.getCustomer().getOrderList();
    for(Orders order : orderList){
        if(order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.IN_PROGRESS){
            ResponseEntity.ok("현재 결제 중인 고객입니다. 결제처리 완료 후 다시 해주세요");
        }
    }
```
5. 결제 가 완료 되면 db에 product 의 quantity 의 개수가 감소하는데 이 때 동시에 같은 상품을 결제 하면

   제고가 없어도 결제를 해버릴 수 있다. 

```
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select o from OrderItem o JOIN FETCH o.product where o.order.id = :orderId")
List<OrderItem> findByOrderConcurrency(Long orderId);
```

동시성 제어를 위해 lock을 걸어서 제어한다.

```
//주문 실패 조건 처리
Customer customer = authUtil.getCustomer();
List<Orders> orders = customer.getOrderList();

Orders currentOrder = null;

for(Orders order : orders){
    if(order.getStatus() == OrderStatus.PENDING){
        currentOrder = order;
        break;
    }
}

List<OrderItem> orderItems = orderItemRepository.findByOrderConcurrency(currentOrder.getId());
```

그런데 findByOrderConcurrency함수를 호출할때 orders 객체의 id를 매개변수로 하는데 orders객체를 얻기 위한 authUtil.getCustomer()함수로 customer얻는 과정에서 문제가 생길 수 있다. 

```
public Customer getCustomer(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Customer customer = customerRepository.findByEmail(email)
            .orElseThrow();
    return customer;
}
```

customerRepository.findByEmail(email) 에서 락이 걸려있지 않기 때문에 customer를 가져오고 이 때 연관되는 order 와 orderItem 들이 있는데 이것 때문에 동시성 제어가 안 될 수 있다.

```
@Test
    public void concurrencyTest() throws InterruptedException {

        //테스트 코드

//        Orders orders = orderRepository.findByTotalPrice(333);
//        Long orderId = orders.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        CountDownLatch latch = new CountDownLatch(2);

//        Orders orders = orderRepository.findByTotalPrice(333);
//        System.out.println("orders total price : "+orders.getTotalPrice());


        executorService.submit(()->{
            try{
                transactionTemplate.execute(status -> {
                    System.out.println("thread 1 진행 중");

//                ObjectMapper objectMapper = new ObjectMapper();
//                String s = objectMapper.writeValueAsString(order);

//                    System.out.println("ordere 체크 : "+ order.getTotalPrice());

                    Orders orders = orderRepository.findByTotalPrice(333);
                    long orderId = orders.getId();
                    entityManager.clear();
                    List<OrderItem> orderItemList = orderItemRepository.findByOrderConcurrency(orderId);
                    System.out.println("thread 종료 전 1");

//                ObjectMapper objectMapper = new ObjectMapper();
//                String s = objectMapper.writeValueAsString(orderItemList);
//                System.out.println("orderItemList : "+s);

                    OrderItem oItem = orderItemList.get(0);
                    System.out.println("thread 종료 전 2");
                    Product myProduct = oItem.getProduct();
                    System.out.println("thread 종료 전 3");
                    System.out.println("product quantity : "+myProduct.getName());
                    System.out.println("product quantity : "+myProduct.getQuantity());
                    myProduct.setQuantity(myProduct.getQuantity()-1);

//                    try {
//                        Thread.sleep(30000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                    System.out.println("thread 종료 직전");
                    return null;
                });
            }catch(Exception e){
                e.printStackTrace();
            }finally {
//                try {
//                    Thread.sleep(30000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                System.out.println("thread 종료");
                latch.countDown();
            }

        });

        executorService.submit(()->{
            try{
                transactionTemplate.execute(status -> {
                    System.out.println("thread 2 시작");
//                    try {
//                        Thread.sleep(30000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }

                    System.out.println("thread 2 진행 중");
                    Orders orders = orderRepository.findByTotalPrice(333);
                    long orderId = orders.getId();
                    entityManager.clear();
                    List<OrderItem> orderItemList = orderItemRepository.findByOrderConcurrency(orderId);
                    System.out.println("2-thread 종료 전 1");

                    OrderItem oItem = orderItemList.get(0);
                    System.out.println("2-thread 종료 전 2");
                    Product myProduct = oItem.getProduct();
                    System.out.println("2-thread 종료 전 3");
            System.out.println("2-product quantity : "+myProduct.getQuantity());
                    myProduct.setQuantity(myProduct.getQuantity()-1);

                    System.out.println("2-thread 종료 직전");
                    System.out.println("2-thread 종료");
                    return  null;
                });
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                latch.countDown();
            }

        });

        latch.await();

        Product product = productRepository.findByName("사과폰");
        Assertions.assertThat(product.getQuantity()).isEqualTo(28);


    }
```

이 상황 처럼 실패 할 것이므로 다른 방법 필요. orderId 클라이언트로 전달 받으면 되긴 함. 찾아보니  trnasaction에서 orders를 가져올 때는 락이 걸려있지 않고 나중에 

락이걸린 메소드로 가져올 때 db에서 가져오지 않고 entitymanager에 영속성 컨텍스트에서 가져온다고 나와있었음.

그래서 ordersid를 이미 알고 있다고 가정하고 테스트를 한다.

> Task :test
> BUILD SUCCESSFUL in 13s
> 4 actionable tasks: 2 executed, 2 up-to-date
> Consider enabling configuration cache to speed up this build: https://docs.gradle.org/9.8.1/userguide/configuration_cache_enabling.html
> 오후 8:24:48: 실행이 완료되었습니다 ':test --tests "com.example.myshopping.productConcurrencyTest.concurrencyTest"'.

그랬더니 테스트 성공. orders가져오는 코드는 그대로 하고 ordersId로 가져와도 안되는 것을 보면 위에 설명이 맞는듯.

중요한건 실제 코드에 적용하려고 하는데 결제 승인 로직에서 락을 거는 게 필요한데 문제는 kakao pay 결제 api를 쓰기 때문에 승인 단계 에서 redirect를 내가 하는 게 아니라서 param으로 orderId를 줄 수 가 없다. redirect url을 클라이언트에서 전달하는데 param으로 전달하면 그 url을 kakao pay api ready 호출 시 전달하면 결제 계정 인증 후 그대로 url로 가서 가능하다.

```
@Transactional
@GetMapping("/approval")
public ResponseEntity<?> orderApproval(@RequestParam String pg_token,
                                       @RequestParam Long orderId) throws Exception {
    System.out.println("approval 요청 들어옴");
   
    List<OrderItem> orderItems = orderItemRepository.findByOrderConcurrency(orderId);
```

이렇게 해결.

#













package bj.gouv.sineb.authservice.application.interceptor;


/*@Slf4j
@Component*/
public class GlobalRequestsFilter /*extends OncePerRequestFilter*/ {

    /*private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRequestsFilter.class);

    private final JournalEventService journalEventService;

    private final UserRepository userRepository;

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private HandlerMapping handlerMapping;

    public GlobalRequestsFilter(JournalEventService journalEventService, UserRepository userRepository) {
        this.journalEventService = journalEventService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest event, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(event);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        final long startTime = System.currentTimeMillis();

        //Start : To get controllers method called

        String controllerMethodCalled = "";
        if (handlerMapping == null) {
            handlerMapping = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getBean(HandlerMapping.class);
        }
        HandlerExecutionChain executionChain = null;
        try {
            executionChain = handlerMapping.getHandler(event);
            if (executionChain != null) {
                executionChain.getHandler();
                controllerMethodCalled = executionChain.getHandler().toString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //End : To get controllers method called

        filterChain.doFilter(requestWrapper, responseWrapper);

        long duration = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                event.getCharacterEncoding());
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        String queryParams = requestWrapper.getQueryString() != null ? requestWrapper.getQueryString().toString() : null ;


        JournalEventRequest journalEventRequest = JournalEventRequest.builder()
                .method(event.getMethod()) // trackingId and id are set in dto
                .requestUrl(event.getRequestURL().toString())
                .requestUri(event.getRequestURI())
                .queryString(getQueryParams(event))
                .protocol(event.getProtocol())
                .remoteAddr(event.getRemoteAddr())
                .remotePort(event.getRemotePort())
                .localAddr(event.getLocalAddr())
                .localPort(event.getLocalPort())
                .scheme(event.getScheme())
                .timestamp(startTime)
                .processingTimeMillis(startTime - Instant.now().getEpochSecond())
                .statusCode(response.getStatus())
                .body(requestBody)
                .build();


        // SAVE JOURNAL EVENT NOW
        journalEventService.save(journalEventRequest);

        log.info("journalEvent : {}",journalEventRequest);


        LOGGER.info(
                "FINISHED PROCESSING : METHOD={}; REQUESTURI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={}; TIM TAKEN={}",
                event.getMethod(), event.getRequestURI(), requestBody, response.getStatus(), responseBody,
                duration);


        responseWrapper.copyBodyToResponse();
    }

    private String getQueryParams(HttpServletRequest event){
        Map<String, String> queryParamsMap = new HashMap<>();
        for (String parameterName : event.getParameterMap().keySet()) {
            String[] values = event.getParameterMap().get(parameterName);
            if (values != null && values.length > 0) {
                queryParamsMap.put(parameterName, values[0]);
            } else {
                queryParamsMap.put(parameterName, null);
            }
        }
        return queryParamsMap.toString();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }*/

}
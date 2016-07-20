////
//// Created by caiyongjian on 16-7-17.
////
//
//#ifndef PROJECT_EXCEPTIONHANDLER_H
//#define PROJECT_EXCEPTIONHANDLER_H
//
//
//#include <signal.h>
//#include <sys/ucontext.h>
//
//class ExceptionHandler {
//public:
//  ExceptionHandler(bool install_handler);
//
//  ExceptionHandler(bool install_handler, void *callback_context);
//
//  // This structure is passed to minidump_writer.h:WriteMinidump via an opaque
//  // blob. It shouldn't be needed in any user code.
//  struct CrashContext {
//    siginfo_t siginfo;
//    pid_t tid;  // the crashing thread.
//    struct ucontext context;
//#if !defined(__ARM_EABI__) && !defined(__mips__)
//    // #ifdef this out because FP state is not part of user ABI for Linux ARM.
//    // In case of MIPS Linux FP state is already part of struct
//    // ucontext so 'float_state' is not required.
//    fpstate_t float_state;
//#endif
//  };
//
//  typedef bool (*HandlerCallback)(const void* crash_context,
//                                  size_t crash_context_size,
//                                  void* context);
//  bool HandleSignal(int sig, siginfo_t *info, void *uc);
//  ExceptionHandler();
//
//  void init();
//
//  // Returns whether out-of-process dump generation is used or not.
//  bool IsOutOfProcess() const {
//    return false;
////    return crash_generation_client_.get() != NULL;
//  }
//
//private:
//  bool GenerateDump(CrashContext *context);
////  scoped_ptr<CrashGenerationClient> crash_generation_client_;
//
//  void* const callback_context_;
//  volatile HandlerCallback crash_handler_;
//};
//
//
//#endif //PROJECT_EXCEPTIONHANDLER_H

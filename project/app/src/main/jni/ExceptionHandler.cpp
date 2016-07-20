////
//// Created by caiyongjian on 16-7-17.
////
//
//#include "ExceptionHandler.h"
//
//
////
//// Created by caiyongjian on 16-7-15.
////
//#include <fcntl.h>
//#include <pthread.h>
//#include <stdio.h>
//#include <sys/syscall.h>
//
//#include <algorithm>
//#include <utility>
//
//#include <algorithm>
//#include <utility>
//#include <vector>
//
//#if defined(__ANDROID__)
//
//#include "linux_syscall_support.h"
//
//#endif
//
//void RestoreHandlersLocked();
//
//namespace {
//  // A wrapper for the tgkill syscall: send a signal to a specific thread.
//  static int tgkill(pid_t tgid, pid_t tid, int sig) {
//    return syscall(__NR_tgkill, tgid, tid, sig);
//    return 0;
//  }
//
//  std::vector<ExceptionHandler*>* g_handler_stack_ = NULL;
//  pthread_mutex_t g_handler_stack_mutex_ = PTHREAD_MUTEX_INITIALIZER;
//}
//
////std::vector<ExceptionHandler*>* g_handler_stack_ = NULL;
//
//ExceptionHandler::ExceptionHandler(bool install_handler,
//                                   void* callback_context)
//    : callback_context_(callback_context) {
//  pthread_mutex_lock(&g_handler_stack_mutex_);
//  if (!g_handler_stack_) {
//    g_handler_stack_ = new std::vector<ExceptionHandler*>;
//  }
//  if (install_handler) {
//    init();
//  }
//  g_handler_stack_->push_back(this);
//  pthread_mutex_unlock(&g_handler_stack_mutex_);
//}
//
//// This function runs in a compromised context: see the top of the file.
//// Runs on the crashing thread.
//bool ExceptionHandler::HandleSignal(int sig, siginfo_t *info, void *uc) {
//  // Allow ourselves to be dumped if the signal is trusted.
//  bool signal_trusted = info->si_code > 0;
//  bool signal_pid_trusted = info->si_code == SI_USER || info->si_code == SI_TKILL;
//  if (signal_trusted || (signal_pid_trusted && info->si_pid == getpid())) {
//    sys_prctl(PR_SET_DUMPABLE, 1, 0, 0, 0);
//  }
//  CrashContext context;
//  // Fill in all the holes in the struct to make Valgrind happy.
//  memset(&context, 0, sizeof(context));
//  memcpy(&context.siginfo, info, sizeof(siginfo_t));
//  memcpy(&context.context, uc, sizeof(struct ucontext));
//#if defined(__aarch64__)
//  struct ucontext *uc_ptr = (struct ucontext*)uc;
//  struct fpsimd_context *fp_ptr =
//      (struct fpsimd_context*)&uc_ptr->uc_mcontext.__reserved;
//  if (fp_ptr->head.magic == FPSIMD_MAGIC) {
//    memcpy(&context.float_state, fp_ptr, sizeof(context.float_state));
//  }
//#elif !defined(__ARM_EABI__)  && !defined(__mips__)
//  // FP state is not part of user ABI on ARM Linux.
//  // In case of MIPS Linux FP state is already part of struct ucontext
//  // and 'float_state' is not a member of CrashContext.
//  struct ucontext *uc_ptr = (struct ucontext*)uc;
//  if (uc_ptr->uc_mcontext.fpregs) {
//    memcpy(&context.float_state,
//           uc_ptr->uc_mcontext.fpregs,
//           sizeof(context.float_state));
//  }
//#endif
//  context.tid = syscall(__NR_gettid);
//  if (crash_handler_ != NULL) {
//    if (crash_handler_(&context, sizeof(context), callback_context_)) {
//      return true;
//    }
//  }
//  return GenerateDump(&context);
//}
//
//// This function may run in a compromised context: see the top of the file.
//bool ExceptionHandler::GenerateDump(CrashContext *context) {
////  if (IsOutOfProcess())
////    return crash_generation_client_->RequestDump(context, sizeof(*context));
//
//  // Allocating too much stack isn't a problem, and better to err on the side
//  // of caution than smash it into random locations.
//  static const unsigned kChildStackSize = 16000;
//  PageAllocator allocator;
//  uint8_t* stack = reinterpret_cast<uint8_t*>(allocator.Alloc(kChildStackSize));
//  if (!stack)
//    return false;
//  // clone() needs the top-most address. (scrub just to be safe)
//  stack += kChildStackSize;
//  my_memset(stack - 16, 0, 16);
//
//  ThreadArgument thread_arg;
//  thread_arg.handler = this;
//  thread_arg.minidump_descriptor = &minidump_descriptor_;
//  thread_arg.pid = getpid();
//  thread_arg.context = context;
//  thread_arg.context_size = sizeof(*context);
//
//  // We need to explicitly enable ptrace of parent processes on some
//  // kernels, but we need to know the PID of the cloned process before we
//  // can do this. Create a pipe here which we can use to block the
//  // cloned process after creating it, until we have explicitly enabled ptrace
//  if (sys_pipe(fdes) == -1) {
//    // Creating the pipe failed. We'll log an error but carry on anyway,
//    // as we'll probably still get a useful crash report. All that will happen
//    // is the write() and read() calls will fail with EBADF
//    static const char no_pipe_msg[] = "ExceptionHandler::GenerateDump "
//        "sys_pipe failed:";
//    logger::write(no_pipe_msg, sizeof(no_pipe_msg) - 1);
//    logger::write(strerror(errno), strlen(strerror(errno)));
//    logger::write("\n", 1);
//
//    // Ensure fdes[0] and fdes[1] are invalid file descriptors.
//    fdes[0] = fdes[1] = -1;
//  }
//
//  const pid_t child = sys_clone(
//      ThreadEntry, stack, CLONE_FILES | CLONE_FS | CLONE_UNTRACED,
//      &thread_arg, NULL, NULL, NULL);
//  if (child == -1) {
//    sys_close(fdes[0]);
//    sys_close(fdes[1]);
//    return false;
//  }
//
//  // Allow the child to ptrace us
//  sys_prctl(PR_SET_PTRACER, child, 0, 0, 0);
//  SendContinueSignalToChild();
//  int status;
//  const int r = HANDLE_EINTR(sys_waitpid(child, &status, __WALL));
//
//  sys_close(fdes[0]);
//  sys_close(fdes[1]);
//
//  if (r == -1) {
//    static const char msg[] = "ExceptionHandler::GenerateDump waitpid failed:";
//    logger::write(msg, sizeof(msg) - 1);
//    logger::write(strerror(errno), strlen(strerror(errno)));
//    logger::write("\n", 1);
//  }
//
//  bool success = r != -1 && WIFEXITED(status) && WEXITSTATUS(status) == 0;
//  if (callback_)
//    success = callback_(minidump_descriptor_, callback_context_, success);
//  return success;
//}
//
//
//stack_t old_stack;
//stack_t new_stack;
//bool stack_installed = false;
//
//// Create an alternative stack to run the signal handlers on. This is done since
//// the signal might have been caused by a stack overflow.
//// Runs before crashing: normal context.
//void InstallAlternateStackLocked() {
//  if (stack_installed)
//    return;
//
//  memset(&old_stack, 0, sizeof(old_stack));
//  memset(&new_stack, 0, sizeof(new_stack));
//
//  // SIGSTKSZ may be too small to prevent the signal handlers from overrunning
//  // the alternative stack. Ensure that the size of the alternative stack is
//  // large enough.
//  static const unsigned kSigStackSize = std::max(16384, SIGSTKSZ);
//
//  // Only set an alternative stack if there isn't already one, or if the current
//  // one is too small.
//  if (sys_sigaltstack(NULL, &old_stack) == -1 || !old_stack.ss_sp ||
//      old_stack.ss_size < kSigStackSize) {
//    new_stack.ss_sp = calloc(1, kSigStackSize);
//    new_stack.ss_size = kSigStackSize;
//
//    if (sys_sigaltstack(&new_stack, NULL) == -1) {
//      free(new_stack.ss_sp);
//      return;
//    }
//    stack_installed = true;
//  }
//}
//
//void InstallDefaultHandler(int sig) {
//  // Android L+ expose signal and sigaction symbols that override the system
//  // ones. There is a bug in these functions where a request to set the handler
//  // to SIG_DFL is ignored. In that case, an infinite loop is entered as the
//  // signal is repeatedly sent to breakpad's signal handler.
//  // To work around this, directly call the system's sigaction.
//  struct kernel_sigaction sa;
//  memset(&sa, 0, sizeof(sa));
//  sys_sigemptyset(&sa.sa_mask);
//  sa.sa_handler_ = SIG_DFL;
//  sa.sa_flags = SA_RESTART;
//  sys_rt_sigaction(sig, &sa, NULL, sizeof(kernel_sigset_t));
//}
//
//
//void SignalHandler(int sig, siginfo_t *info, void *uc) {
//  // All the exception signals are blocked at this point.
//  pthread_mutex_lock(&g_handler_stack_mutex_);
//
//  // Sometimes, Breakpad runs inside a process where some other buggy code
//  // saves and restores signal handlers temporarily with 'signal'
//  // instead of 'sigaction'. This loses the SA_SIGINFO flag associated
//  // with this function. As a consequence, the values of 'info' and 'uc'
//  // become totally bogus, generally inducing a crash.
//  //
//  // The following code tries to detect this case. When it does, it
//  // resets the signal handlers with sigaction + SA_SIGINFO and returns.
//  // This forces the signal to be thrown again, but this time the kernel
//  // will call the function with the right arguments.
//  struct sigaction cur_handler;
//  if (sigaction(sig, NULL, &cur_handler) == 0 &&  (cur_handler.sa_flags & SA_SIGINFO) == 0) {
//    // Reset signal handler with the right flags.
//    sigemptyset(&cur_handler.sa_mask);
//    sigaddset(&cur_handler.sa_mask, sig);
//
//    cur_handler.sa_sigaction = SignalHandler;
//    cur_handler.sa_flags = SA_ONSTACK | SA_SIGINFO;
//
//    if (sigaction(sig, &cur_handler, NULL) == -1) {
//      // When resetting the handler fails, try to reset the
//      // default one to avoid an infinite loop here.
//      InstallDefaultHandler(sig);
//    }
//    pthread_mutex_unlock(&g_handler_stack_mutex_);
//    return;
//  }
//
//  bool handled = false;
//  for (int i = g_handler_stack_->size() - 1; !handled && i >= 0; --i) {
//    handled = (*g_handler_stack_)[i]->HandleSignal(sig, info, uc);
//  }
//
//  // Upon returning from this signal handler, sig will become unmasked and then
//  // it will be retriggered. If one of the ExceptionHandlers handled it
//  // successfully, restore the default handler. Otherwise, restore the
//  // previously installed handler. Then, when the signal is retriggered, it will
//  // be delivered to the appropriate handler.
//  if (handled) {
//    InstallDefaultHandler(sig);
//  } else {
//    RestoreHandlersLocked();
//  }
//
//  pthread_mutex_unlock(&g_handler_stack_mutex_);
//
//  // info->si_code <= 0 iff SI_FROMUSER (SI_FROMKERNEL otherwise).
//  if (info->si_code <= 0 || sig == SIGABRT) {
//    // This signal was triggered by somebody sending us the signal with kill().
//    // In order to retrigger it, we have to queue a new signal by calling
//    // kill() ourselves.  The special case (si_pid == 0 && sig == SIGABRT) is
//    // due to the kernel sending a SIGABRT from a user request via SysRQ.
//    if (tgkill(getpid(), syscall(__NR_gettid), sig) < 0) {
//      // If we failed to kill ourselves (e.g. because a sandbox disallows us
//      // to do so), we instead resort to terminating our process. This will
//      // result in an incorrect exit code.
//      _exit(1);
//    }
//  } else {
//    // This was a synchronous signal triggered by a hard fault (e.g. SIGSEGV).
//    // No need to reissue the signal. It will automatically trigger again,
//    // when we return from the signal handler.
//  }
//}
//
//const int kExceptionSignals[] = {
//    SIGSEGV, SIGABRT, SIGFPE, SIGILL, SIGBUS
//};
//const int kNumHandledSignals =
//    sizeof(kExceptionSignals) / sizeof(kExceptionSignals[0]);
//struct sigaction old_handlers[kNumHandledSignals];
//bool handlers_installed = false;
//
//// This function runs in a compromised context: see the top of the file.
//// Runs on the crashing thread.
//// static
//void RestoreHandlersLocked() {
//  if (!handlers_installed)
//    return;
//
//  for (int i = 0; i < kNumHandledSignals; ++i) {
//    if (sigaction(kExceptionSignals[i], &old_handlers[i], NULL) == -1) {
//      InstallDefaultHandler(kExceptionSignals[i]);
//    }
//  }
//  handlers_installed = false;
//}
//
//bool InstallHandlersLocked() {
//  if (handlers_installed)
//    return false;
//
//  // Fail if unable to store all the old handlers.
//  for (int i = 0; i < kNumHandledSignals; ++i) {
//    if (sigaction(kExceptionSignals[i], NULL, &old_handlers[i]) == -1)
//      return false;
//  }
//
//  struct sigaction sa;
//  memset(&sa, 0, sizeof(sa));
//  sigemptyset(&sa.sa_mask);
//
//  // Mask all exception signals when we're handling one of them.
//  for (int i = 0; i < kNumHandledSignals; ++i)
//    sigaddset(&sa.sa_mask, kExceptionSignals[i]);
//
//  sa.sa_sigaction = SignalHandler;
//  sa.sa_flags = SA_ONSTACK | SA_SIGINFO;
//
//  for (int i = 0; i < kNumHandledSignals; ++i) {
//    if (sigaction(kExceptionSignals[i], &sa, NULL) == -1) {
//      // At this point it is impractical to back out changes, and so failure to
//      // install a signal is intentionally ignored.
//    }
//  }
//  handlers_installed = true;
//  return true;
//}
//
//
//void ExceptionHandler::init() {
//  InstallAlternateStackLocked();
//  InstallHandlersLocked();
//}

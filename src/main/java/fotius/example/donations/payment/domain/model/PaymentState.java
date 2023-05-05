package fotius.example.donations.payment.domain.model;

public enum PaymentState {
    
    NEW {
        @Override
        public boolean canChangeTo(PaymentState toState) {
            return toState == INITIATED || toState == CANCELED;
        }
    },
    
    INITIATED {
        @Override
        public boolean canChangeTo(PaymentState toState) {
            return toState == COMPLETED || toState == CANCELED;
        }
    },
    
    COMPLETED {
        @Override
        public boolean canChangeTo(PaymentState toState) {
            return false;
        }
    },
    
    CANCELED {
        @Override
        public boolean canChangeTo(PaymentState toState) {
            return false;
        }
    };

    public abstract boolean canChangeTo(PaymentState toState);
}
